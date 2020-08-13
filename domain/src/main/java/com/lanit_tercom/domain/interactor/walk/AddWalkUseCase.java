package com.lanit_tercom.domain.interactor.walk;

import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface AddWalkUseCase {

    interface Callback{

        void onWalkAdded();

        void onError(ErrorBundle errorBundle);
    }

    void execute(WalkDto walkDto, AddWalkUseCase.Callback callback);
}
