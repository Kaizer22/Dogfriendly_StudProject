package com.lanit_tercom.data.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lanit_tercom.data.entity.UserEntity;
import com.lanit_tercom.data.firebase.cache.UserCache;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FirebaseUserEntityStore implements UserEntityStore {

    private static final String CHILD_USERS = "Users";
    private UserEntity userEntity = new UserEntity();

    private final UserCache userCache;

    protected DatabaseReference referenceDatabase;


    public FirebaseUserEntityStore(UserCache userCache){ //userCache???
        this.userCache = userCache;

        referenceDatabase = FirebaseDatabase.getInstance().getReference();
        // Test getUserById() and getAllUsers() methods
        getUserById("1", new UserByIdCallback() {
            @Override
            public void onUserLoaded(UserEntity userEntity) {
                System.out.println("USER: ");
                System.out.println(userEntity.toString());
            }

            @Override
            public void onError(Exception exception) {
                System.out.println("Error");
            }
        });

        getAllUsers(new UserListCallback() {
            @Override
            public void onUsersListLoaded(List<UserEntity> users) {
                System.out.println("USER: ");
                for (UserEntity user: users){
                    System.out.println(user.toString());
                }
            }

            @Override
            public void onError(Exception exception) {
                System.out.println("Error");
            }
        });
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


    private void putUserEntityInCache(String userId, UserEntity userEntity){
        if (userCache != null){
            this.userCache.saveUser(userId, userEntity);
        }
    }

}
