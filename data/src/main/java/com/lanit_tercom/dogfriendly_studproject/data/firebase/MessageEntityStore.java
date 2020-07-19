package com.lanit_tercom.dogfriendly_studproject.data.firebase;

import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;

import java.sql.Timestamp;
import java.util.List;

public interface MessageEntityStore {

    interface MessageByIdCallback {
        void onMessageLoaded(MessageEntity message);

        void onError(Exception exception);
    }

    interface MessageListCallback {
        void onMessagesListLoaded(List<MessageEntity> messages);

        void onError(Exception exception);
    }

    interface MessageCreateCallback {
        void onMessageCreated();

        void onError(Exception exception);
    }

    interface MessageUpdateCallback {
        void onMessageUpdated();

        void onError(Exception exception);
    }

    interface MessageDeleteCallback {
        void onMessageDeleteCallback();

        void onError(Exception exception);
    }

    void getAllMessages(MessageEntityStore.MessageListCallback messageListCallback);

    void getMessageById(String id, MessageEntityStore.MessageByIdCallback messageByIdCallback);

    void postMessage(MessageEntity messageEntity, MessageEntityStore.MessageCreateCallback messageCreateCallback);

    void editMessage(MessageEntity messageEntity, MessageEntityStore.MessageUpdateCallback messageUpdateCallback);

    void deleteMessage(MessageEntity messageEntity, MessageEntityStore.MessageDeleteCallback messageDeleteCallback);
}
