package com.lanit_tercom.domain.interactor.create;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface CreateUserDetailsUseCase {
    interface Callback {
        void onUserDataCreated(UserDto userDto);

        void onError(ErrorBundle errorBundle);
    }

    void execute(Callback callback);
}
