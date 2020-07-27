package com.lanit_tercom.domain.interactor.channel;


import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface AddChannelUseCase {

    interface Callback {
        void onChannelAdded();

        void onError(ErrorBundle errorBundle);
    }

    void execute(ChannelDto channelDto, AddChannelUseCase.Callback callback);
}
