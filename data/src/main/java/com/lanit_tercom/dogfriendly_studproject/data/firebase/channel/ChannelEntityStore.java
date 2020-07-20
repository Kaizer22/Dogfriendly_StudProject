package com.lanit_tercom.dogfriendly_studproject.data.firebase.channel;


import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;

import java.util.List;

public interface ChannelEntityStore {

    interface ChannelDetailCallback {
        void onChannelEdited();

        void onError(Exception exception);
    }

    interface ChannelsDetailCallback {
        void onChannelsLoaded(List<ChannelEntity> channels);

        void onError(Exception exception);
    }

    void getChannels(ChannelsDetailCallback callback);
    void addChannel(ChannelEntity channel, ChannelDetailCallback callback);
    void deleteChannel(String channelId, ChannelDetailCallback callback);
}
