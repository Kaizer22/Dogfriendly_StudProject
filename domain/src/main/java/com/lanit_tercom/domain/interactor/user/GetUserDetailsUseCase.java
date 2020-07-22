package com.lanit_tercom.domain.interactor.user;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.Interactor;

public interface GetUserDetailsUseCase extends Interactor {

    interface Callback {
        void onUserDataLoaded(UserDto userDto);

        void onError(ErrorBundle errorBundle);
    }

    void execute(String userId, Callback callback);
}
