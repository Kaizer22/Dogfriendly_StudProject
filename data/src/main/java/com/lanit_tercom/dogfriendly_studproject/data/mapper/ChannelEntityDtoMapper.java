package com.lanit_tercom.dogfriendly_studproject.data.mapper;

import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;
import com.lanit_tercom.domain.dto.ChannelDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChannelEntityDtoMapper {

    public ChannelEntity map1(ChannelDto channelDto){
        if(channelDto == null) return null;
        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setId(channelDto.getId());
        channelEntity.setName(channelDto.getName());
        channelEntity.setLastMessage(channelDto.getLastMessage());
        channelEntity.setLastMessageOwner(channelDto.getLastMessageOwner());
        channelEntity.setTimestamp(channelDto.getTimestamp());

        List<HashMap<String, String>> members = new ArrayList<>();

        for(String id: channelDto.getMembers()){
            HashMap<String, String> pair = new HashMap<>();
            pair.put("userId", id);
            members.add(pair);
        }

        channelEntity.setMembers(members);

        return channelEntity;
    }


    public ChannelDto map2(ChannelEntity channelEntity){
        if(channelEntity == null) return null;
        ChannelDto channelDto = new ChannelDto();
        channelDto.setId(channelEntity.getId());
        channelDto.setName(channelEntity.getName());
        channelDto.setLastMessage(channelEntity.getLastMessage());
        channelDto.setLastMessageOwner(channelEntity.getLastMessageOwner());
        channelDto.setTimestamp(channelEntity.getTimestamp());

        List<String> members = new ArrayList<>();

        for(HashMap<String, String> pair: channelEntity.getMembers())
            members.add(pair.get("userId"));

        channelDto.setMembers(members);

        return channelDto;
    }


    public List<ChannelDto> mapForList(List<ChannelEntity> channels){
        if(channels == null) return null;

        List<ChannelDto> channelDtoList = new ArrayList<>();

        for(ChannelEntity channelEntity: channels)
            channelDtoList.add(map2(channelEntity));

        return channelDtoList;
    }

}
