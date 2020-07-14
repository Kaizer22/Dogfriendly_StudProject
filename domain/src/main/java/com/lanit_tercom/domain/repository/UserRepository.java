package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

public interface UserRepository {

    /**
     * Callback used to be notified when either a user has been loaded or an error happened.
     */

    interface UserDetailsCallback {
        void onUserLoaded(UserDto userDto);

        void onError(ErrorBundle errorBundle); // ErrorBundle errorBundle
    }

    interface UserListCallback{
        void onUsersListLoaded(List<UserDto> usersDtoList);
        void onError(ErrorBundle errorBundle);
    }

    /**
     * Get an {@link UserDto} by id.
     *
     * @param userId The user id used to retrieve user data.
     * @param userCallback A {@link UserDetailsCallback} used for notifying clients.
     */

    void getUserById(final String userId, UserDetailsCallback userCallback);

    void getAllUsers(UserListCallback userListCallback);
}
