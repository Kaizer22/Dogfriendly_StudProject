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

    void getUserById(final String userId, UserDetailsCallback userCallback);

    void getUsers(UsersDetailsCallback userCallback);

    void createUser(UserDetailsCallback userCallback);

    void editUserById(String name, UserDetailsCallback userCallback);

}

