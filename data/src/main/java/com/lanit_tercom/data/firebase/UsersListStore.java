package com.lanit_tercom.data.firebase;

import com.lanit_tercom.data.entity.UserEntity;

import java.util.List;

public interface UsersListStore {


    interface UserListCallback{
        void onUsersListLoaded(List<UserEntity> users);
        void onError(Exception exception);
    }

    void getAllUsers(UserListCallback userListCallback);
}
