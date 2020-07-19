package com.lanit_tercom.domain.interactor.user.get;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.Interactor;

import java.util.List;

public interface GetUsersDetailsUseCase extends Interactor {

    interface Callback {
        void onUsersDataLoaded(List<UserDto> users);

        void onError(ErrorBundle errorBundle);
    }

    void execute(Callback callback);
}
