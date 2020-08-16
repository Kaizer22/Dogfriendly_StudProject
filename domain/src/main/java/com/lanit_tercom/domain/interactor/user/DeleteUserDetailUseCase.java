package com.lanit_tercom.domain.interactor.user;

import com.lanit_tercom.domain.exception.ErrorBundle;

public interface DeleteUserDetailUseCase {
    interface Callback {
        void onUserDeleted();

        void onError(ErrorBundle errorBundle);
    }

    void execute(String id, DeleteUserDetailUseCase.Callback callback);
}
