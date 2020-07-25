package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.R;
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
    private List<MessageModel> messagesList;

    private DeleteMessageUseCase deleteMessage;
    private EditMessageUseCase editMessage;
    private GetMessagesUseCase getMessages;
    private PostMessageUseCase postMessage;
    //private GetUserDetailsUseCase getUser;

    private MessageDtoModelMapper modelMapper;

    //Пока не решена проблема с view из BasePresenter
    //При обращении из ChatPresenter:
    //'view' has private access in 'com.lanit_tercom.dogfriendly_studproject.mvp.presenter.BasePresenter'
    private ChatView view;

    public ChatPresenter(String channelID, AuthManager authManager, DeleteMessageUseCase deleteMessage,
                         EditMessageUseCase editMessage, GetMessagesUseCase getMessages,
                         PostMessageUseCase postMessage){   //, GetUserDetailsUseCase getUser){
        this.channelID = channelID;
        this.authManager = authManager;
        this.deleteMessage = deleteMessage;
        this.editMessage = editMessage;
        this.getMessages = getMessages;
        this.postMessage = postMessage;
        //this.getUser = getUser;
        modelMapper = new MessageDtoModelMapper();
        messagesList = new LinkedList<>();
    }

    public ChatPresenter(AuthManager authManager) {
        this.authManager = authManager;
    }

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

        messageModel.setText(message);
        messageModel.setTime(System.currentTimeMillis());

        //Добавление сообщения в БД через domain слой
        MessageDto messageDto = modelMapper.map1(messageModel);
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
        getMessagesFromDB();
        //Обновление данных о чате
        //getChannel();
    }

    public String getCurrentUserID(){
        return authManager.getCurrentUserId();
    }

    public List<MessageModel> getMessages() {
        return messagesList;
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
                            modelMapper.map2(message));
                }
                view.renderMessages();
                view.hideLoading();
            }
            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }
}
