package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface UserRepository {

    void getUserById(String userId, UserDetailsCallback userCallback);

    /**
     * Callback used to be notified when either a user has been loaded or an error happened.
     */

    interface UserDetailsCallback {
        void onUserLoaded(UserDto userDto);

        void onError(ErrorBundle errorBundle); // ErrorBundle errorBundle
    }

    /**
     * Get an {@link UserDto} by id.
     *
     * @param userId The user id used to retrieve user data.
     * @param userCallback A {@link UserDetailsCallback} used for notifying clients.
     */

    //void getUserById(final String userId, UserDetailsCallback userCallback);
}
