package com.lanit_tercom.dogfriendly_studproject.data.mapper;

import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.dto.UserDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// TODO исправить MessageDto (методы/поля)

public class MessageEntityDtoMapper {
    public MessageEntityDtoMapper(){}
    public MessageEntity map1(MessageDto messageDto){
        if (messageDto == null)
            return null;
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(messageDto.getMessageId());
        // Нет метода
        //messageEntity.setChannelId(messageDto.getChannelId());
        messageEntity.setTimestamp(new Timestamp(messageDto.getTimestamp()));
        messageEntity.setUserName(messageDto.getSenderId());
        messageEntity.setBody(messageDto.getText());
        return messageEntity;
    }
    public MessageDto map2(MessageEntity messageEntity){
        if (messageEntity == null)
            return null;
        // Нужно изменить domain (убрать receiver, добавить channel...
        MessageDto messageDto = new MessageDto(messageEntity.getId(),
                messageEntity.getUserName(),
                "",
                messageEntity.getBody(),
                messageEntity.getTimestamp().getTime());
        return messageDto;

    }
    public List<MessageDto> mapForList(List<MessageEntity> messages){
        if (messages == null)
            return null;
        List<MessageDto> messagesDtoList = new ArrayList<>();
        for (MessageEntity messageEntity: messages){
            MessageDto messageDto = new MessageDto(messageEntity.getId(),
                    messageEntity.getUserName(),
                    "",
                    messageEntity.getBody(),
                    messageEntity.getTimestamp().getTime());
            messagesDtoList.add(messageDto);
        }
        return messagesDtoList;
    }
}