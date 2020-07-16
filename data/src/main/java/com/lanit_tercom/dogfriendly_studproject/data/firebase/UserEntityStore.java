package com.lanit_tercom.dogfriendly_studproject.data.firebase;

import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;

import java.util.List;

public interface UserEntityStore {

    interface UserByIdCallback{
        void onUserLoaded(UserEntity user);
        void onError(Exception exception);
    }

    interface UserListCallback{
        void onUsersListLoaded(List<UserEntity> users);
        void onError(Exception exception);
    }

    void getAllUsers(UserListCallback userListCallback);
    void getUserById(String id, UserByIdCallback userByIdCallback);
}
