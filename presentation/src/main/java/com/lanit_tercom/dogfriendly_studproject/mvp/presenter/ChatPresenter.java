package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import android.util.Log;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mapper.ChannelDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mapper.MessageDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChatView;
import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase;
import com.lanit_tercom.domain.interactor.channel.impl.GetChannelsUseCaseImpl;
import com.lanit_tercom.domain.interactor.message.DeleteMessageUseCase;
import com.lanit_tercom.domain.interactor.message.EditMessageUseCase;
import com.lanit_tercom.domain.interactor.message.GetMessagesUseCase;
import com.lanit_tercom.domain.interactor.message.PostMessageUseCase;
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *  Презентер для работы с диалогом между пользователями
 *  Здесь содержится логика, предоставляющая данные для отображения,
 *  а также логика обработки действий пользователя
 *  @author dshebut@rambler.ru
 */
public class ChatPresenter extends BasePresenter {
    private final String SENDING_MESSAGE_EVENT = "Отправка сообщения...";

    private AuthManager authManager;

    private String channelID;
    private String addresseeID;
    private String userID;
    //Пока статус пользователя никак не отображен в модели
    private List<MessageModel> messagesList;
    private ChannelModel channelModel;

    private DeleteMessageUseCase deleteMessage;
    private EditMessageUseCase editMessage;
    private GetMessagesUseCase getMessages;
    private PostMessageUseCase postMessage;
    private GetUserDetailsUseCase getUser;

    private MessageDtoModelMapper messageMapper;
    private UserDtoModelMapper userMapper;

    private ChatView view;

    private boolean isChannelEmpty;

    public ChatPresenter(ChannelModel channelModel, AuthManager authManager, DeleteMessageUseCase deleteMessage,
                         EditMessageUseCase editMessage, GetMessagesUseCase getMessages,
                         PostMessageUseCase postMessage, GetUserDetailsUseCase getUser){
        this.channelModel = channelModel;
        this.channelID = channelModel.getId();
        this.userID = authManager.getCurrentUserId();
        this.authManager = authManager;
        this.deleteMessage = deleteMessage;
        this.editMessage = editMessage;
        this.getMessages = getMessages;
        this.postMessage = postMessage;
        this.getUser = getUser;
        messageMapper = new MessageDtoModelMapper();
        messagesList = new LinkedList<>();
    }

    //public ChatPresenter(AuthManager authManager) {
        //this.authManager = authManager;
    //}

    //Задаем View, к которой будет привязан presenter
    public void setView(ChatView view){
        this.view = view;
    }

    public void sendMessage(String message){
        this.view.showLoading();
        this.view.showProgressMessage(SENDING_MESSAGE_EVENT);

        MessageModel messageModel = new MessageModel();
        messageModel.setSenderID(authManager.getCurrentUserId());
        messageModel.setChatID(channelID);
        messageModel.setTime(new Date(System.currentTimeMillis()));
        messageModel.setText(message);

        //Добавление сообщения в БД через domain слой
        MessageDto messageDto = messageMapper.map1(messageModel);
        postMessage.execute(messageDto, new PostMessageUseCase.Callback() {
            @Override
            public void onMessagePosted() {
                refreshData();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    public void editMessage(MessageModel messageModel){
        //Редактирование сообщения в БД через domain слой
        MessageDto messageDto = messageMapper.map1(messageModel);
        editMessage.execute(messageDto, new EditMessageUseCase.Callback() {
            @Override
            public void onMessageEdited() {
                refreshData();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    public void deleteMessage(MessageModel messageModel){
        //Удаление сообщения из БД через domain слой
        MessageDto messageDto = messageMapper.map1(messageModel);
        deleteMessage.execute(messageDto, new DeleteMessageUseCase.Callback() {
            @Override
            public void onMessageDeleted() {
                refreshData();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    public void refreshData(){
        //Получение диалога
        getMessagesFromDB();
        //Обновление данных о чате
        refreshChannelData();
    }

    private void refreshChannelData() {
        List<String> members = channelModel.getMembers();
        if (members.size() == 2){
            String otherUserID = members.get(0).equals(userID) ?
                    members.get(1) : members.get(0);
            addresseeID = otherUserID;
            getUser.execute(otherUserID, new GetUserDetailsUseCase.Callback() {
                @Override
                public void onUserDataLoaded(UserDto userDto) {
                    //Пока что статус никак не отображен в модели пользователя
                    view.updateChannelInfo(userDto.getName(),
                            "offline", userDto.getAvatar());
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    errorBundle.getException().printStackTrace();
                }
            });

        }
    }

    public String getCurrentUserID(){
        return authManager.getCurrentUserId();
    }

    public String getAddresseeID(){
        return addresseeID;
    }

    public List<MessageModel> getMessages() {
        return messagesList;
    }

    public boolean isChannelEmpty(){
        return isChannelEmpty;
    }

    //private void getChannel() {
        //getChat.execute(channelID);
    //}

    private void getMessagesFromDB(){
        messagesList.clear();
        getMessages.execute(channelID, new GetMessagesUseCase.Callback() {
            @Override
            public void onMessagesDataLoaded(List<MessageDto> messages) {
                for (MessageDto message: messages) {
                   messagesList.add(
                            messageMapper.map2(message));
                }
                isChannelEmpty = messagesList.size() == 0;
                view.renderMessages();
                view.hideLoading();
            }
            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
