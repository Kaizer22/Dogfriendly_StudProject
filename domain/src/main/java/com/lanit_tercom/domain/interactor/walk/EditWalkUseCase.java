package com.lanit_tercom.domain.interactor.walk;

import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface EditWalkUseCase {

    interface Callback{

        void onWalkEdited();

        void onError(ErrorBundle errorBundle);
    }

    void execute(WalkDto walkDto, EditWalkUseCase.Callback callback);
}
