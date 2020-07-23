package com.lanit_tercom.domain.interactor.channel;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public interface DeleteChannelUseCase {

    interface Callback {
        void onChannelDeleted();

        void onError(ErrorBundle errorBundle);
    }

    void execute(String userId, ChannelDto channelDto, DeleteChannelUseCase.Callback callback);
}
