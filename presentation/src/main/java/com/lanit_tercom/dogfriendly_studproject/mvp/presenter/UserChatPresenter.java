package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;

/**
 *  Презентер для работы с диалогом между пользователями
 *  Здесь содержится логика, предоставляющая данные для отображения,
 *  а также логика обработки действий пользователя
 *  @author dshebut@rambler.ru
 */
public class UserChatPresenter extends BasePresenter {

    private AuthManager authManager;

    String addresseeID;

    public UserChatPresenter(AuthManager authManager){
        this.authManager = authManager;
    }
    public void sendMessage(String message){
        MessageModel messageModel = new MessageModel();
        messageModel.setSenderID(authManager.getCurrentUserId());
        messageModel.setReceiverID(addresseeID);

        messageModel.setText(message);
        messageModel.setTime(System.currentTimeMillis());

        //Добавление сообщения в БД через domain слой
    }

    public void editMessage(MessageModel messageModel){
        //Редактирование сообщения в БД через domain слой
    }

    public void deleteMessage(MessageModel messageModel){
        //Удаление сообщения из БД через domain слой
    }

    public void refreshData(){
        //Получение диалога
        //Обновление данных о собеседнике
    }


}
