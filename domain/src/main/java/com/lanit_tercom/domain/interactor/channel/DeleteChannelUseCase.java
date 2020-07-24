package com.lanit_tercom.domain.interactor.channel;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface DeleteChannelUseCase {

    interface Callback {
        void onChannelDeleted();

        void onError(ErrorBundle errorBundle);
    }

    void execute(String userId, ChannelDto channelDto, DeleteChannelUseCase.Callback callback);
}
