package com.lanit_tercom.dogfriendly_studproject.data.firebase.user;

import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

public interface UserEntityStore {

    interface Error{
        void onError(ErrorBundle errorBundle);
    }

    interface UserByIdCallback extends Error {
        void onUserLoaded(UserEntity user);

    }

    interface UserListCallback extends Error{
        void onUsersListLoaded(List<UserEntity> users);
    }

    interface UserCreateCallback extends Error{
        void onUserCreated();
    }

    interface UserEditCallback extends Error{
        void onUserEdited();
    }

    void getAllUsers(UserListCallback userListCallback);
    void getUserById(String id, UserByIdCallback userByIdCallback);
    void createUser(UserEntity user, UserCreateCallback userCreateCallback);
    void editUser(UserEntity user, UserEditCallback userEditCallback);
}
