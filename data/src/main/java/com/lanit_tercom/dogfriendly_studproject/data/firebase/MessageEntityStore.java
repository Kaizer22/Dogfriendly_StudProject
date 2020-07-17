package com.lanit_tercom.dogfriendly_studproject.data.firebase;

import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;

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

    void getAllMessages(MessageEntityStore.MessageListCallback messageListCallback);

    void getMessageById(String id, MessageEntityStore.MessageByIdCallback messageByIdCallback);
}
