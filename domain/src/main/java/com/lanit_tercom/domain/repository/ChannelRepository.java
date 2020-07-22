package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

public interface ChannelRepository {

    interface Error{
        void onError(ErrorBundle errorBundle);
    }

    interface ChannelDetailsCallback extends Error{
        void onChannelLoaded(ChannelDto channelDto); //ChannelDto
    }

    interface ChannelsDetailsCallback extends Error{
        void onChannelsLoaded(List<ChannelDto> channels); //List<ChannelDto>
    }

    void getChannelByID(final String channelId, ChannelDetailsCallback channelCallback);

    void getChannels(ChannelsDetailsCallback channelsCallback);
}
