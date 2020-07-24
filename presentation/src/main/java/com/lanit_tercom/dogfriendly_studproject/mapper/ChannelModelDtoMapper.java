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
                channelDto.getId(),
                channelDto.getName(),
                channelDto.getLastMessage(),
                channelDto.getLastMessageOwner(),
                channelDto.getTimestamp(),
                channelDto.getMembers());

        return channelModel;
    }


    public ChannelDto mapToDto(ChannelModel channelModel){
        if (channelModel == null){
            return null;
        }

        ChannelDto channelDto = new ChannelDto();

        channelDto.setId(channelModel.getId());
        channelDto.setName(channelModel.getName());
        channelDto.setLastMessage(channelModel.getLastMessage());
        channelDto.setLastMessageOwner(channelModel.getLastMessageOwner());
        channelDto.setTimestamp(channelModel.getTimestamp());
        channelDto.setMembers(channelModel.getMembers());

        return channelDto;
    }



}
