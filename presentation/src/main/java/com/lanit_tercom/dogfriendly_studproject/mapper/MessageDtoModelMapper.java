package com.lanit_tercom.dogfriendly_studproject.mapper;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.library.data.mapper.BaseMapper;

import java.sql.Timestamp;

public class MessageDtoModelMapper extends BaseMapper<MessageDto, MessageModel> {

    @Override
    public MessageDto map1(MessageModel o2) {
        if (o2 == null){
            return null;
        }
        MessageDto messageDto = new MessageDto(o2.getSenderID(), o2.getText());
        messageDto.setChannelId(o2.getChatID());
        messageDto.setId(o2.getMessageID());
        messageDto.setTimestamp(new Timestamp(o2.getTime()));
        return messageDto;
    }

    @Override
    public MessageModel map2(MessageDto o1) {
        if (o1 == null){
            return null;
        }
        String messageID = o1.getId();
        String chatID = o1.getChannelId();
        String senderID = o1.getUserName();
        long time = o1.getTimestamp().getTime();
        String text = o1.getBody();

        return new MessageModel(messageID, senderID, chatID, text, time);
    }
}
