package com.lanit_tercom.domain.interactor.user.edit;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface EditUserDetailsUseCase {

    interface Callback {
        void onUserDataEdited(UserDto userDto);

        void onError(ErrorBundle errorBundle);
    }

    void execute(String userId, Callback callback);
}
