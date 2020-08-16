package com.lanit_tercom.domain.interactor.user;


import com.lanit_tercom.domain.exception.ErrorBundle;


public interface DeletePetUseCase {
    interface Callback {
        void onPetDeleted();

        void onError(ErrorBundle errorBundle);
    }

    void execute(String userId, String petId, DeletePetUseCase.Callback callback);
}
