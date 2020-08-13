package com.lanit_tercom.domain.interactor.walk;

import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.channel.DeleteChannelUseCase;

public interface DeleteWalkUseCase {

    interface Callback{

        void onWalkDeleted();

        void onError(ErrorBundle errorBundle);
    }

    void execute(WalkDto walkDto, DeleteChannelUseCase.Callback callback);
}
