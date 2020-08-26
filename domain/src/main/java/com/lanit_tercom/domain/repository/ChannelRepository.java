package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

/**
 * @author nikolaygorokhov1@gmail.com
 */
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

    interface ChannelEditCallback extends Error {
        void onChannelEdited();
    }

    interface ChannelDeleteCallback extends Error {
        void onChannelDeleted();
    }

    void getChannels(String userId, ChannelsLoadCallback callback);

    void addChannel(ChannelDto channelDto, ChannelAddCallback callback);

    void editChannel(ChannelDto channelDto, ChannelEditCallback callback);

    void deleteChannel(String userId, ChannelDto channelDto, ChannelDeleteCallback callback); //String userId ???

}
