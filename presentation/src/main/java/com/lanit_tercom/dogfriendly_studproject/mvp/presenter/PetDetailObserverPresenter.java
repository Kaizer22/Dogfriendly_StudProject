package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import android.util.Log;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStore;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailObserverView;
import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase;

import java.util.LinkedList;

public class PetDetailObserverPresenter extends BasePresenter {

    private PetDetailObserverView view;
    private AuthManager authManager;

    private AddChannelUseCase addChannel;

    public PetDetailObserverPresenter(AddChannelUseCase addChannel){
        this.addChannel = addChannel;
        authManager = new AuthManagerFirebaseImpl();
    }
    @Override
    public void onDestroy() {

    }


    public void addChannel(String hostID, String viewingUserId, String viewingUserName){
        long timestamp = System.currentTimeMillis();
        LinkedList<String> members = new LinkedList<>();
        members.add(hostID);
        members.add(viewingUserId);
        ChannelDto channelDto = new ChannelDto("id будет присвоен при добавлении в БД",
                viewingUserName, "Пусто...", viewingUserId, timestamp, members);
        addChannel.execute(channelDto, new AddChannelUseCase.Callback() {
            @Override
            public void onChannelAdded() {
                //view.navigateToChat();
                Log.d("PET_OBSERVER","CHANNEL CREATED");
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    public void setView(PetDetailObserverView view) {
        this.view = view;
    }

    public String getCurrentUserId(){
        return authManager.getCurrentUserId();
    }
}
