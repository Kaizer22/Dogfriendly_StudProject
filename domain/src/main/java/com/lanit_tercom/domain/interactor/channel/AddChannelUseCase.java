package com.lanit_tercom.domain.interactor.channel;


import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public interface AddChannelUseCase {

    interface Callback {
        void onChannelAdded();

        void onError(ErrorBundle errorBundle);
    }

    void execute(ChannelDto channelDto, AddChannelUseCase.Callback callback);
}
