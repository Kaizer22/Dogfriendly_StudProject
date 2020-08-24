package com.lanit_tercom.dogfriendly_studproject.data.firebase.user;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.data.entity.PetEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.UserCache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseUserEntityStore implements UserEntityStore {

    private static final String CHILD_USERS = "Users";
    private UserEntity userEntity = new UserEntity();

    private UserCache userCache;

    private AuthManager authManager;

    protected DatabaseReference referenceDatabase;

    public FirebaseUserEntityStore(UserCache userCache) {
        referenceDatabase = FirebaseDatabase.getInstance().getReference().child(CHILD_USERS);
        this.userCache = userCache;
        authManager = new AuthManagerFirebaseImpl();
    }

    public void getUserById(final String id, final UserByIdCallback userByIdCallback) {
        referenceDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userEntity = dataSnapshot.getValue(UserEntity.class);
                userEntity.setId(id);
                userByIdCallback.onUserLoaded(userEntity); // return UserEntity
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                userByIdCallback.onError(new RepositoryErrorBundle(databaseError.toException()));
            }
        });
    }

    public void getAllUsers(final UserListCallback userListCallback) {
        final List<UserEntity> users = new ArrayList<>();
        referenceDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    UserEntity userEntity = keyNode.getValue(UserEntity.class);
                    userEntity.setId(keyNode.getKey());
                    users.add(userEntity);
                }
                userListCallback.onUsersListLoaded(users); // return all users from Realtime Database
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                userListCallback.onError(new RepositoryErrorBundle(databaseError.toException()));
            }
        });
    }


    public void getUserListById(List<String> usersId, UserListByIdCallback userListByIdCallback) {
        final List<UserEntity> users = new ArrayList<>();
        referenceDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    UserEntity userEntity = keyNode.getValue(UserEntity.class);
                    userEntity.setId(keyNode.getKey());
                    if (usersId.contains(userEntity.getId())) {
                        users.add(userEntity);
                    }
                }
                userListByIdCallback.onUserListByIdLoaded(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                userListByIdCallback.onError(new RepositoryErrorBundle(databaseError.toException()));
            }
        });
    }


    @Override
    public void createUser(UserEntity user, UserCreateCallback userCreateCallback) {
        String firebaseId = referenceDatabase.push().getKey();
        user.setId(firebaseId);
        Map<String, Object> map = new HashMap<>();
        map.put(user.getId(), user);
        referenceDatabase.updateChildren(map)
                .addOnSuccessListener(aVoid -> userCreateCallback.onUserCreated())
                .addOnFailureListener(e -> userCreateCallback.onError(new RepositoryErrorBundle(e)));
    }

    @Override
    public void editUser(UserEntity user, UserEditCallback userEditCallback) {
        Map<String, Object> map = new HashMap<>();
        map.put(user.getId(), user);
        referenceDatabase.updateChildren(map)
                .addOnSuccessListener(aVoid -> userEditCallback.onUserEdited())
                .addOnFailureListener(e -> userEditCallback.onError(new RepositoryErrorBundle(e)));
    }

    @Override
    public void deleteUser(String id, UserDeleteCallback userDeleteCallback) {
        referenceDatabase.child(id).removeValue()
                .addOnSuccessListener(aVoid -> userDeleteCallback.onUserDeleted())
                .addOnFailureListener(e -> userDeleteCallback.onError(new RepositoryErrorBundle(e)));
    }

    @Override
    public void addPet(String id, PetEntity pet, AddPetCallback addPetCallback) {
        Map<String, Object> map = new HashMap<>();
        map.put(pet.getId(), pet);
        referenceDatabase.child(id).child("pets").updateChildren(map)
                .addOnSuccessListener(aVoid -> addPetCallback.onPetAdded())
                .addOnFailureListener(e -> addPetCallback.onError(new RepositoryErrorBundle(e)));
    }

    @Override
    public void deletePet(String userId, String petId, DeletePetCallback deletePetCallback) {
        referenceDatabase.child(userId).child("pets").child(petId).removeValue()
                .addOnSuccessListener(aVoid -> deletePetCallback.onPetDeleted())
                .addOnFailureListener(e -> deletePetCallback.onError(new RepositoryErrorBundle(e)));
    }

    private void putUserEntityInCache(String userId, UserEntity userEntity) {
        if (userCache != null) {
            this.userCache.saveUser(userId, userEntity);
        }
    }

}