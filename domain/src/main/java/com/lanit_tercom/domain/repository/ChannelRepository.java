package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

public interface ChannelRepository {

    interface Error {
        void onError(ErrorBundle errorBundle);
    }

    interface ChannelsLoadCallback extends Error {
        void onChannelsLoaded(List<ChannelDto> channels);
    }


    interface ChannelAddCallback extends Error {
        void onChannelAdded();
    }

    interface ChannelDeleteCallback extends Error {
        void onChannelDeleted();
    }

    void getChannels(String userId, ChannelsLoadCallback callback);

    void addChannel(ChannelDto channelDto, ChannelAddCallback callback);

    void deleteChannel(String userId, ChannelDto channelDto, ChannelDeleteCallback callback);
}
