package com.lanit_tercom.dogfriendly_studproject.data.firebase.user;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.UserCache;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FirebaseUserEntityStore implements UserEntityStore {

    private static final String CHILD_USERS = "Users";
    private UserEntity userEntity = new UserEntity();

    private UserCache userCache; //

    protected DatabaseReference referenceDatabase;

    public FirebaseUserEntityStore(UserCache userCache){
        referenceDatabase = FirebaseDatabase.getInstance().getReference();
        this.userCache = userCache;
    }


    public void getUserById(final String id, final UserByIdCallback userByIdCallback){
        referenceDatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot snapshot: dataSnapshot.child(CHILD_USERS).getChildren()){
                   if (id.equals(snapshot.getKey())) {
                       userEntity = snapshot.getValue(UserEntity.class);
                       userEntity.setId(id);
                   }
               }
               userByIdCallback.onUserLoaded(userEntity); // return UserEntity
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Log.e(TAG, "onCancelled", databaseError.toException());
           }
       });
    }

    public void getAllUsers(final UserListCallback userListCallback){
        final List<UserEntity> users = new ArrayList<>();
        referenceDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode: dataSnapshot.child(CHILD_USERS).getChildren()){
                    keys.add(keyNode.getKey());
                    UserEntity userEntity = keyNode.getValue(UserEntity.class);
                    userEntity.setId(keyNode.getKey());
                    users.add(userEntity);
                }
                userListCallback.onUsersListLoaded(users); // return all users from Realtime Database
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void createUser(UserEntity user, UserCreateCallback userCreateCallback) {
        String firebaseId = referenceDatabase.child(CHILD_USERS).push().getKey();
        user.setId(firebaseId);
        Map<String, Object> map = new HashMap<>();
        map.put(user.getId(), user);
        referenceDatabase.child(CHILD_USERS).updateChildren(map)
                .addOnSuccessListener(aVoid -> userCreateCallback.onUserCreated())
                .addOnFailureListener(e -> userCreateCallback.onError(new RepositoryErrorBundle(e)));
    }

    @Override
    public void editUser(UserEntity user, UserEditCallback userEditCallback) {
        Map<String, Object> map = new HashMap<>();
        map.put(user.getId(), user);
        referenceDatabase.child(CHILD_USERS).updateChildren(map)
                .addOnSuccessListener(aVoid -> userEditCallback.onUserEdited())
                .addOnFailureListener(e -> userEditCallback.onError(new RepositoryErrorBundle(e)));
    }

    private void putUserEntityInCache(String userId, UserEntity userEntity){
        if (userCache != null){
            this.userCache.saveUser(userId, userEntity);
        }
    }

}
