package com.lanit_tercom.domain.interactor.get;

import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.Interactor;

public interface GetChannelList extends Interactor {

    interface Callback{

        //void onChannelDataLoaded(ChannelDto channelDto);

        void onError(ErrorBundle errorBundle);
    }

    void execute(String channelId, GetChannelList.Callback callback);
}
