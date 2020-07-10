package com.lanit_tercom.data.firebase;

import com.lanit_tercom.data.entity.UserEntity;

import java.util.List;

public interface UserEntityStore {


    interface DataStatus{
        void allUsersLoaded(List<UserEntity> users);
        void userEntityLoaded(UserEntity user);
    }

    void getUserById(String id, DataStatus dataStatus);

    void getAllUsers(DataStatus dataStatus);
}
