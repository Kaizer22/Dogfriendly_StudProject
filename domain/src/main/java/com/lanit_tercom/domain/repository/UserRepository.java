package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

public interface UserRepository {

    interface Error {
        void onError(ErrorBundle errorBundle);
    }

    interface UserDetailsCallback extends Error {
        void onUserLoaded(UserDto userDto);
    }

    interface UsersDetailsCallback extends Error {
        void onUsersLoaded(List<UserDto> users);
    }

    interface UserCreateCallback extends Error {
        void onUserCreated();
    }
    interface UserEditCallback extends Error {
        void onUserEdited();
    }


    void getUserById(String userId, UserDetailsCallback userCallback);

    void getUsers(UsersDetailsCallback userCallback);

    void createUser(UserDto userDto, UserCreateCallback userCallback);

    void editUser(UserDto userDto, UserEditCallback userCallback);

}