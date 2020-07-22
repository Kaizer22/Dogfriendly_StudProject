package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mapper.MessageDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChatView;
import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.message.DeleteMessageUseCase;
import com.lanit_tercom.domain.interactor.message.EditMessageUseCase;
import com.lanit_tercom.domain.interactor.message.GetMessagesUseCase;
import com.lanit_tercom.domain.interactor.message.PostMessageUseCase;
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase;

import java.util.List;

/**
 *  Презентер для работы с диалогом между пользователями
 *  Здесь содержится логика, предоставляющая данные для отображения,
 *  а также логика обработки действий пользователя
 *  @author dshebut@rambler.ru
 */
public class ChatPresenter extends BasePresenter {

    private AuthManager authManager;

    String channelID;

    DeleteMessageUseCase deleteMessage;
    EditMessageUseCase editMessage;
    GetMessagesUseCase getMessages;
    PostMessageUseCase postMessage;
    GetUserDetailsUseCase getUser;

    MessageDtoModelMapper modelMapper;

    ChatView view;

    public ChatPresenter(AuthManager authManager, DeleteMessageUseCase deleteMessage,
                         EditMessageUseCase editMessage, GetMessagesUseCase getMessages,
                         PostMessageUseCase postMessage, GetUserDetailsUseCase getUser){
        this.authManager = authManager;
        this.deleteMessage = deleteMessage;
        this.editMessage = editMessage;
        this.getMessages = getMessages;
        this.postMessage = postMessage;
        this.getUser = getUser;
        modelMapper = new MessageDtoModelMapper();
    }

    public ChatPresenter(AuthManager authManager) {
        this.authManager = authManager;
    }

    //Задаем View, к которой будет привязан presenter
    public void setView(ChatView view){
        this.view = view;
    }

    public void sendMessage(String message){
        MessageModel messageModel = new MessageModel();
        messageModel.setSenderID(authManager.getCurrentUserId());
        messageModel.setChatID(channelID);

        messageModel.setText(message);
        messageModel.setTime(System.currentTimeMillis());

        //Добавление сообщения в БД через domain слой
        MessageDto messageDto = modelMapper.map1(messageModel);
        postMessage.execute(messageDto, new PostMessageUseCase.Callback() {
            @Override
            public void onMessagePosted() {
                //TODO действия после отправки сообщения

            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });

        MessageProviderTemp.addMessage(messageModel);

    }

    public void editMessage(MessageModel messageModel){
        //Редактирование сообщения в БД через domain слой
        MessageDto messageDto = modelMapper.map1(messageModel);
        editMessage.execute(messageDto, new EditMessageUseCase.Callback() {
            @Override
            public void onMessageEdited() {
                //TODO действия после редактирования сообщения
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    public void deleteMessage(MessageModel messageModel){
        //Удаление сообщения из БД через domain слой
        MessageDto messageDto = modelMapper.map1(messageModel);
        deleteMessage.execute(messageDto, new DeleteMessageUseCase.Callback() {
            @Override
            public void onMessageDeleted() {
                //TODO действия после удаления сообщения
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    public void refreshData(){
        //Получение диалога
        getMessages();
        //Обновление данных о чате
        //getChannel();
    }

    public String getCurrentUserID(){
        return authManager.getCurrentUserId();
    }


    //private void getChannel() {
        //getChat.execute(channelID);
    //}



    private void getMessages(){
        getMessages.execute(channelID, new GetMessagesUseCase.Callback() {
            @Override
            public void onMessagesDataLoaded(List<MessageDto> messages) {
                //TODO действия после получения списка сообщений
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }
}
