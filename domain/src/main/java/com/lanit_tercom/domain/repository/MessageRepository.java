package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

public interface MessageRepository {

    interface Error {
        void onError(ErrorBundle errorBundle);
    }

    //to delete message
    interface MessageDeleteCallback extends Error{
        void onMessageDeleted();


    }

    //to edit message
    interface MessageEditCallback extends Error{
        void onMessageEdited();


    }

    //to post message
    interface MessagePostCallback extends Error{
        void onMessagePosted();


    }

    //to get messages
    interface MessagesDetailCallback extends Error{
        void onMessagesLoaded(List<MessageDto> messages);
    }

    interface LastMessagesDetailCallback extends Error{
        void onLastMessagesLoaded(List<MessageDto> lastMessages);
    }

    void getMessages(String channelId, MessagesDetailCallback callback);

    void getLastMessages(List<String> channelsId, LastMessagesDetailCallback callback);

    void postMessage(MessageDto message, MessagePostCallback callback);

    void editMessage(MessageDto message, MessageEditCallback callback);

    void deleteMessage(MessageDto message, MessageDeleteCallback callback);
}