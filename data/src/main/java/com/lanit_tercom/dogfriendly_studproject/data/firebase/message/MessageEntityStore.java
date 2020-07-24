package com.lanit_tercom.dogfriendly_studproject.data.firebase.message;

import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

public interface MessageEntityStore {

    interface Error {
        void onError(ErrorBundle errorBundle);
    }

    interface MessageDetailCallback {
        void onMessageLoaded(MessageEntity message);

        void onError(Exception exception);
    }

    interface MessagesDetailCallback {
        void onMessagesLoaded(List<MessageEntity> messages);

        void onError(Exception exception);
    }

    interface MessagePostCallback {
        void onMessagePosted();

        void onError(ErrorBundle errorBundle);
    }

    interface MessageEditCallback {
        void onMessageEdited();

        void onError(ErrorBundle errorBundle);
    }

    interface MessageDeleteCallback {
        void onMessageDeleted();

        void onError(ErrorBundle errorBundle);
    }

    void getMessages(MessagesDetailCallback messagesDetailCallback);

    void getMessage(String id, MessageDetailCallback messageDetailCallback);

    void postMessage(MessageEntity messageEntity, MessagePostCallback messageCreateCallback);

    void editMessage(MessageEntity messageEntity, MessageEditCallback messageUpdateCallback);

    void deleteMessage(MessageEntity messageEntity, MessageDeleteCallback messageDeleteCallback);
}
