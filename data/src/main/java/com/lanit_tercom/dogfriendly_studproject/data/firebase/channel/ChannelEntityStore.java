package com.lanit_tercom.dogfriendly_studproject.data.firebase.channel;


import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public interface ChannelEntityStore {

    interface DeleteChannelCallback{
        void onChannelDeleted();

        void onError(ErrorBundle errorBundle);
    }

    //Если мы делает DeleteChannel то разумно ChannelDetailCallback переименовать в AddChannelCallback
    //Так как кроме как для добавления он нигде не используется теперь.
    interface AddChannelCallback {
        void onChannelAdded();

        void onError(ErrorBundle errorBundle);
    }

    interface ChannelsDetailCallback {
        void onChannelsLoaded(List<ChannelEntity> channels);

        void onError(ErrorBundle errorBundle);
    }

    void getChannels(String userId, ChannelsDetailCallback callback);

    void addChannel(ChannelEntity channel, AddChannelCallback callback);

    void deleteChannel(String userId, ChannelEntity channel, DeleteChannelCallback callback);
}
