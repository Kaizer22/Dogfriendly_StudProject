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

    interface CreateOrEditCallback extends Error {
        void onUserCreatedOrEdited();
    }


    void getUserById(String userId, UserDetailsCallback userCallback);

    void getUsers(UsersDetailsCallback userCallback);

    void createUser(UserDto userDto, CreateOrEditCallback userCallback);

    void editUserById(String id, UserDto userDto, CreateOrEditCallback userCallback);

}