package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelListModel;

public class UserChannelListPresenter extends BasePresenter {

    private AuthManager authManager;

    public UserChannelListPresenter(AuthManager authManager){
        this.authManager = authManager;
    }

    public void openDialog(ChannelListModel dialogModel){
        //TODO Открытые выбранного диалога
    }

    public void deleteDialog(ChannelListModel dialogModel){
        //TODO Удаление выбранного диалога
    }

    public void turnOffNotifications(ChannelListModel dialogModel){
        //TODO Отключение уведомлений в выбранном диалоге
    }

    public AuthManager getAuthManager(){
        return authManager;
    }

}
