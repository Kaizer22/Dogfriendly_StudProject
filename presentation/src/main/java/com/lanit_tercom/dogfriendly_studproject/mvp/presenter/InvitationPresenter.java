package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import android.widget.Toast;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mapper.WalkModelDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.WalkModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.InvitationView;
import com.lanit_tercom.dogfriendly_studproject.ui.activity.InvitationActivity;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.InvitationFragment;
import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.walk.AddWalkUseCase;

import java.util.ArrayList;
import java.util.List;

public class InvitationPresenter extends BasePresenter {

    private AuthManager authManager;
    //private InvitationView invitationView;

    private AddWalkUseCase addWalkUseCase;
    private WalkModelDtoMapper walkModelDtoMapper;

    public InvitationPresenter(AuthManager authManager,
                               AddWalkUseCase addWalkUseCase){
        this.authManager = authManager;
        this.addWalkUseCase = addWalkUseCase;

        walkModelDtoMapper = new WalkModelDtoMapper();
    }

    /*public void setView(InvitationView view){
        this.invitationView = view;
    }*/

    public void addWalk(WalkModel walkModel){
        List<String> members = new ArrayList<>();
        members.add(authManager.getCurrentUserId());
        walkModel.setCreator(authManager.getCurrentUserId());
        walkModel.setMembers(members);


        WalkDto walkDto = walkModelDtoMapper.mapToDto(walkModel);
        addWalkUseCase.execute(walkDto, new AddWalkUseCase.Callback() {
            @Override
            public void onWalkAdded() {
                //TODO как выбрать нужную прогулку???
                //((InvitationActivity)invitationView).navigateToInvitationScreen(authManager.getCurrentUserId());
            }

            @Override
            public void onError(ErrorBundle errorBundle) {

            }
        });
    }


}
