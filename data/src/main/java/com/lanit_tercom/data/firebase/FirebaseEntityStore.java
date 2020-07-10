package com.lanit_tercom.data.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lanit_tercom.data.entity.UserEntity;

import androidx.annotation.NonNull;


import com.lanit_tercom.data.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FirebaseEntityStore implements UserEntityStore{

    private static final String CHILD_USERS = "Users";
    private List<UserEntity> users = new ArrayList<>();
    private UserEntity userEntity = new UserEntity();

    protected DatabaseReference referenceDatabase;


    public FirebaseEntityStore(){
        referenceDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void getUserById(final String id, final DataStatus dataStatus){
        referenceDatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot snapshot: dataSnapshot.child(CHILD_USERS).getChildren()){
                   if (id.equals(snapshot.getKey())) {
                       userEntity = snapshot.getValue(UserEntity.class);
                       userEntity.setId(id);
                   }
               }
               dataStatus.userEntityLoaded(userEntity); // return UserEntity
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Log.e(TAG, "onCancelled", databaseError.toException());
           }
       });
    }


    public void getAllUsers(final DataStatus dataStatus){
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
                dataStatus.allUsersLoaded(users); // return all users from Realtime Database
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

}
