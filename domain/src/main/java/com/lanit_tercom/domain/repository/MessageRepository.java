package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

public interface MessageRepository {

    interface Error {
        void onError(ErrorBundle errorBundle);
    }

    //to delete message
    interface MessageDeleteCallback{
        void onMessageDeleted();

        void onError(ErrorBundle errorBundle);
    }

    //to edit message
    interface MessageEditCallback{
        void onMessageEdited();

        void onError(ErrorBundle errorBundle);
    }

    //to post message
    interface MessagePostCallback{
        void onMessagePosted();

        void onError(ErrorBundle errorBundle);
    }

    //to get messages
    interface MessagesDetailCallback{
        void onMessagesLoaded(List<MessageDto> messages);

        void onError(ErrorBundle errorBundle);
    }

    void getMessages(String channelId, MessagesDetailCallback callback);

    void postMessage(MessageDto message, MessagePostCallback callback);

    void editMessage(MessageDto message, MessageEditCallback callback);

    void deleteMessage(MessageDto message, MessageDeleteCallback callback);
}
