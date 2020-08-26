package com.lanit_tercom.domain.interactor.channel;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface EditChannelUseCase {

    interface Callback{
        void onChannelEdited();

        void onError(ErrorBundle errorBundle);
    }

    void execute(ChannelDto channelDto, EditChannelUseCase.Callback callback);
}
