package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.WalkDetailsView;

public class WalkPresenter extends BasePresenter {

    private WalkDetailsView walkDetailsView;

    private AuthManager authManager;


    public WalkPresenter(){}

    public void setView(WalkDetailsView view){
        this.walkDetailsView = view;
    }


    private void showWalkDetailInView(){

    }

    @Override
    public void onDestroy() {

    }
}
