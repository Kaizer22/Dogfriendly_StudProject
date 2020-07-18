package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

/**
 * Интерфейс представляющий функционал репозитория сообщений
 * @author nikolaygorokhov1@gmail.com
 */
public interface MessageRepository {

    interface Error {
        void onError(ErrorBundle errorBundle);
    }

    interface MessageDetailCallback{
        void onMessageLoaded(MessageDto messageDto);

        void onError(ErrorBundle errorBundle);
    }

    interface MessagesDetailCallback{
        void onMessagesLoaded(List<MessageDto> messages);

        void onError(ErrorBundle errorBundle);
    }

    void getMessages(String peerId, MessagesDetailCallback callback);

    void postMessage(MessageDto message, MessageDetailCallback callback);

    void editMessage(MessageDto editedMessage, MessageDetailCallback callback);

    void deleteMessage(MessageDto message, MessageDetailCallback callback);
}
