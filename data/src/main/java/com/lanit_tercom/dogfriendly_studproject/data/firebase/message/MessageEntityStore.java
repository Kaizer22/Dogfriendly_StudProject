package com.lanit_tercom.dogfriendly_studproject.data.firebase.message;

import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

public interface MessageEntityStore {

    interface Error {
        void onError(ErrorBundle errorBundle);
    }

    interface MessageDetailCallback extends Error {
        void onMessageLoaded(MessageEntity message);
    }

    interface MessagesDetailCallback extends Error{
        void onMessagesLoaded(List<MessageEntity> messages);
    }

    interface MessagePostCallback extends Error {
        void onMessagePosted();
    }

    interface MessageEditCallback extends Error{
        void onMessageEdited();
    }

    interface MessageDeleteCallback extends Error{
        void onMessageDeleted();
    }

    void getMessages(String channelId, MessagesDetailCallback messagesDetailCallback);


    void postMessage(MessageEntity messageEntity, MessagePostCallback messagePostCallback);

    void editMessage(MessageEntity messageEntity, MessageEditCallback messageEditCallback);

    void deleteMessage(MessageEntity messageEntity, MessageDeleteCallback messageDeleteCallback);
}
