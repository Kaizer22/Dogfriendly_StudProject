package com.lanit_tercom.domain.interactor.channel;


import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public interface GetChannelsUseCase {

    interface Callback {
        void onChannelsLoaded(List<ChannelDto> channels);

        void onError(ErrorBundle errorBundle);
    }

    void execute(String channelId, GetChannelsUseCase.Callback callback);
}
