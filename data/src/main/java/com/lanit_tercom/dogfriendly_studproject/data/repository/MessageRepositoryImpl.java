package com.lanit_tercom.dogfriendly_studproject.data.repository;

import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.MessageListException;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.MessageEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.MessageEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.MessageEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper;
import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.repository.MessageRepository;

import java.util.List;

public class MessageRepositoryImpl implements MessageRepository {

    private static MessageRepositoryImpl INSTANCE;

    public static synchronized MessageRepositoryImpl getInstance(MessageEntityStoreFactory messageEntityStoreFactory,
                                                                 MessageEntityDtoMapper messageEntityDtoMapper) {
        if (INSTANCE == null) {
            INSTANCE = new MessageRepositoryImpl(messageEntityStoreFactory, messageEntityDtoMapper);
        }
        return INSTANCE;
    }

    private final MessageEntityStoreFactory messageEntityStoreFactory;
    private final MessageEntityDtoMapper messageEntityDtoMapper;

    protected MessageRepositoryImpl(MessageEntityStoreFactory messageEntityStoreFactory,
                                 MessageEntityDtoMapper messageEntityDtoMapper) {

        if (messageEntityStoreFactory == null || messageEntityDtoMapper == null) {
            throw new IllegalArgumentException("Invalid null parameters in constructor!!!");
        }

        this.messageEntityStoreFactory = messageEntityStoreFactory;
        this.messageEntityDtoMapper = messageEntityDtoMapper;
    }

    @Override
    public void getMessages(String peerId, MessagesDetailCallback callback) {
        MessageEntityStore messageEntityStore = messageEntityStoreFactory.create();
        messageEntityStore.getMessages(new MessageEntityStore.MessagesDetailCallback() {
            @Override
            public void onMessagesLoaded(List<MessageEntity> messages) {
                List<MessageDto> messagesDtoList = MessageRepositoryImpl.this.messageEntityDtoMapper.mapForList(messages);
                if (messagesDtoList != null)
                    callback.onMessagesLoaded(messagesDtoList);
                else callback.onError(new RepositoryErrorBundle(new MessageListException())); // ошибка для списка сообщений
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(new RepositoryErrorBundle(exception));
            }
        });

    }

    @Override
    public void postMessage(MessageDto message, MessageDetailCallback callback) {
        MessageEntityStore messageEntityStore = messageEntityStoreFactory.create();
        MessageEntity messageEntity = messageEntityDtoMapper.map1(message);
        messageEntityStore.postMessage(messageEntity, new MessageEntityStore.MessagePostCallback() {
            @Override
            public void onMessagePosted() {
                callback.onMessageLoaded(message);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }
        });

    }

    @Override
    public void editMessage(MessageDto editedMessage, MessageEditCallback callback) {
        MessageEntityStore messageEntityStore = messageEntityStoreFactory.create();
        MessageEntity messageEntity = messageEntityDtoMapper.map1(editedMessage);
        messageEntityStore.editMessage(messageEntity, new MessageEntityStore.MessageEditCallback() {
            @Override
            public void onMessageEdited() {
                callback.onMessageEdited();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }
        });
    }

    @Override
    public void deleteMessage(MessageDto message, MessageEditCallback callback) {
        MessageEntityStore messageEntityStore = messageEntityStoreFactory.create();
        MessageEntity messageEntity = messageEntityDtoMapper.map1(message);
        messageEntityStore.deleteMessage(messageEntity, new MessageEntityStore.MessageDeleteCallback() {
            @Override
            public void onMessageDeleted() {
                callback.onMessageEdited();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }
        });
    }
}
