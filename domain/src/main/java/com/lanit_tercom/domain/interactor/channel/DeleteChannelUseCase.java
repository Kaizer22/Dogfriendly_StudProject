package com.lanit_tercom.domain.interactor.channel;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public interface DeleteChannelUseCase {

    interface Callback {
        void onChannelDeleted(); //ChannelDto channelDto

        void onError(ErrorBundle errorBundle);
    }

    void execute(String userID, ChannelDto channelDto, DeleteChannelUseCase.Callback callback);
}
