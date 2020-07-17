package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.DialogListModel;

public class UserDialogListPresenter extends BasePresenter {

    private AuthManager authManager;

    public UserDialogListPresenter(AuthManager authManager){
        this.authManager = authManager;
    }

    public void openDialog(DialogListModel dialogModel){
        //TODO Открытые выбранного диалога
    }

    public void deleteDialog(DialogListModel dialogModel){
        //TODO Удаление выбранного диалога
    }

    public void turnOffNotifications(DialogListModel dialogModel){
        //TODO Отключение уведомлений в выбранном диалоге
    }

}
