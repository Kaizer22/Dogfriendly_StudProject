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

    interface AddChannelCallback {
        void onChannelAdded();

        void onError(ErrorBundle errorBundle);
    }

    interface EditChannelCallback {
        void onChannelEdited();

        void onError(ErrorBundle errorBundle);
    }

    interface GetChannelsCallback {
        void onChannelsLoaded(List<ChannelEntity> channels);

        void onError(ErrorBundle errorBundle);
    }

    void getChannels(String userId, GetChannelsCallback callback);

    void addChannel(ChannelEntity channel, AddChannelCallback callback);

    void deleteChannel(String userId, ChannelEntity channel, DeleteChannelCallback callback);

    void editChannel(ChannelEntity channelEntity, EditChannelCallback callback);
}
