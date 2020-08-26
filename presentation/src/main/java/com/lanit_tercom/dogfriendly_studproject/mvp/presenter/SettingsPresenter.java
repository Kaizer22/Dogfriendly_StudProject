package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.SettingsView;

public class SettingsPresenter extends BasePresenter {

    private AuthManager authManager;
    private SettingsView view;

    public SettingsPresenter(){
        authManager = new AuthManagerFirebaseImpl();
    }

    public void signOut(){
        authManager.signOut(new AuthManager.SignOutCallback() {
            @Override
            public void onSignOutFinished() {
                view.signOut();
            }
        });
    }


    @Override
    public void onDestroy() {

    }
}
