package com.lanit_tercom.domain.interactor.walk;

import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface GetWalkUseCase {

    interface Callback{
        void onWalkDataLoaded(WalkDto walkDto);

        void onError(ErrorBundle errorBundle);
    }

    void execute(String userId, String walkId, GetWalkUseCase.Callback callback);
}
