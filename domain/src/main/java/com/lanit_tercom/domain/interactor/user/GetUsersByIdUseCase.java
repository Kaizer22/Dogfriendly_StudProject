package com.lanit_tercom.domain.interactor.user;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.Interactor;

import java.util.List;

public interface GetUsersByIdUseCase extends Interactor {

    interface Callback {
        void onUsersDataLoaded(List<UserDto> users);

        void onError(ErrorBundle errorBundle);
    }

    void execute(List<String> usersId, Callback callback);
}
