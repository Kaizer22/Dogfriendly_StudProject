package com.lanit_tercom.dogfriendly_studproject.mapper;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.domain.dto.ChannelDto;

public class ChannelModelDtoMapper {

    public ChannelModelDtoMapper(){}

    /**
     * Transform a {@link ChannelDto} into an {@link ChannelModel}.
     *
     * @param channelDto Object to be transformed.
     * @return {@link ChannelModel}.
     */


    public ChannelModel mapToModel(ChannelDto channelDto){
        if (channelDto == null){
            return null;
        }

        ChannelModel channelModel = new ChannelModel(
                channelDto.getChannelId(),
                channelDto.getLastMessage(),
                channelDto.getLastMessageOwner(),
                channelDto.getLastMessageTime());

        return channelModel;
    }


    public ChannelDto mapToDto(ChannelModel channelModel){
        if (channelModel == null){
            return null;
        }

        ChannelDto channelDto = new ChannelDto(
                channelModel.getChannelID(),
                channelModel.getLastMessage(),
                channelModel.getLastMessageOwner(),
                channelModel.getLastMessageTime());

        return channelDto;
    }



}
