package com.lanit_tercom.data.firebase;

import com.lanit_tercom.data.entity.UserEntity;

import java.util.List;

public interface UserEntityStore {

    interface UserByIdCallback{
        void onUserLoaded(UserEntity user);
        void onError(Exception exception);
    }

    void getUserById(String id, UserByIdCallback userByIdCallback);
}
