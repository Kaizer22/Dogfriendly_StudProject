package com.lanit_tercom.domain.interactor.get;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.Interactor;

public interface GetChannelListUseCase extends Interactor {

    interface Callback{

        void onChannelDataLoaded(ChannelDto channelDto); //ChannelDto

        void onError(ErrorBundle errorBundle);
    }

    void execute(String channelId, GetChannelListUseCase.Callback callback);
}
