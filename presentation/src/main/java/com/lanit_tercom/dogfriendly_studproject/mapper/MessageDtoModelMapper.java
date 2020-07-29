package com.lanit_tercom.dogfriendly_studproject.mapper;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.library.data.mapper.BaseMapper;

import java.sql.Timestamp;
import java.util.Date;

public class MessageDtoModelMapper extends BaseMapper<MessageDto, MessageModel> {

    @Override
    public MessageDto map1(MessageModel o2) {
        if (o2 == null){
            return null;
        }
        MessageDto messageDto = new MessageDto(o2.getSenderID(), o2.getText());
        messageDto.setChannelId(o2.getChatID());
        messageDto.setId(o2.getMessageID());
        messageDto.setTimestamp(o2.getTime().getTime());
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
        Date time = new Date(o1.getTimestamp());
        String text = o1.getBody();
        MessageModel messageModel = new MessageModel(messageID, senderID, chatID, text);
        messageModel.setTime(time);
        return messageModel;
    }
}
