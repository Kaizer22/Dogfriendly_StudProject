package com.lanit_tercom.dogfriendly_studproject.mapper;


import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.domain.dto.ChannelDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ChannelDtoModelMapper {

    public ChannelDtoModelMapper(){}

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

        channelModel.setPinned(channelDto.isPinned());
        channelModel.setOffNotification(channelDto.isOffNotification());

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
        channelDto.setPinned(channelModel.isPinned());
        channelDto.setOffNotification(channelModel.isNotification());
        channelDto.setMembers(channelModel.getMembers());

        return channelDto;
    }

    public List<ChannelModel> transformList(List<ChannelDto> channelDtoList){
        List<ChannelModel> channelModelList;

        if (channelDtoList != null && !channelDtoList.isEmpty()){
            channelModelList = new ArrayList<>();
            for (ChannelDto channelDto: channelDtoList){
                channelModelList.add(mapToModel(channelDto));
            }
        }
        else{
            channelModelList = Collections.emptyList();
        }
        return channelModelList;
    }



}
