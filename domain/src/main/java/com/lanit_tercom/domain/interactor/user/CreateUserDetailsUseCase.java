package com.lanit_tercom.domain.interactor.user;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface CreateUserDetailsUseCase {

    interface Callback {
        void onUserDataCreated();

        void onError(ErrorBundle errorBundle);
    }

    void execute(UserDto userDto, Callback callback);
}
