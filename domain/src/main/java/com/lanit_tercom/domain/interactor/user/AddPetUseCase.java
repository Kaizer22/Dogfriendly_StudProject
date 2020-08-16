package com.lanit_tercom.domain.interactor.user;

import com.lanit_tercom.domain.dto.PetDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface AddPetUseCase {
    interface Callback {
        void onPetAdded();

        void onError(ErrorBundle errorBundle);
    }

    void execute(String id, PetDto petDto, AddPetUseCase.Callback callback);
}
