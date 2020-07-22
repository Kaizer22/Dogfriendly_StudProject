package com.lanit_tercom.dogfriendly_studproject.data.mapper;

import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.dto.UserDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MessageEntityDtoMapper {
    public MessageEntityDtoMapper(){}
    public MessageEntity map1(MessageDto messageDto){
        if (messageDto == null)
            return null;
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setChannelId(messageDto.getChannelId());
        messageEntity.setId(messageDto.getId());
        messageEntity.setUserName(messageDto.getUserName());
        messageEntity.setBody(messageDto.getBody());
        messageEntity.setTimestamp(messageDto.getTimestamp());
        return messageEntity;
    }
    public MessageDto map2(MessageEntity messageEntity){
        if (messageEntity == null)
            return null;
        MessageDto messageDto = new MessageDto(messageEntity.getUserName(), messageEntity.getBody());
        messageDto.setChannelId(messageEntity.getChannelId());
        messageDto.setId(messageEntity.getId());
        messageDto.setTimestamp(messageEntity.getTimestamp());
        return messageDto;

    }
    public List<MessageDto> mapForList(List<MessageEntity> messages){
        if (messages == null)
            return null;
        List<MessageDto> messagesDtoList = new ArrayList<>();
        for (MessageEntity messageEntity: messages){
            MessageDto messageDto = new MessageDto(messageEntity.getUserName(), messageEntity.getBody());
            messageDto.setChannelId(messageEntity.getChannelId());
            messageDto.setId(messageEntity.getId());
            messageDto.setTimestamp(messageEntity.getTimestamp());
            messagesDtoList.add(messageDto);
        }
        return messagesDtoList;
    }
}