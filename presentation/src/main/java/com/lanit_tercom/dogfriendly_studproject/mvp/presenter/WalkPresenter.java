package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mapper.WalkDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.WalkModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.WalkDetailsView;
import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase;
import com.lanit_tercom.domain.interactor.user.GetUsersByIdUseCase;
import com.lanit_tercom.domain.interactor.user.GetUsersDetailsUseCase;
import com.lanit_tercom.domain.interactor.walk.DeleteWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.EditWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.GetWalkUseCase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WalkPresenter extends BasePresenter {

    private WalkDetailsView walkDetailsView;
    List<UserDto> members = new LinkedList<>();

    private AuthManager authManager;
    private WalkDtoModelMapper walkDtoModelMapper;
    private UserDtoModelMapper userDtoModelMapper;

    private GetWalkUseCase getWalkUseCase;
    private EditWalkUseCase editWalkUseCase;
    private DeleteWalkUseCase deleteWalkUseCase;
    private GetUserDetailsUseCase getUserDetailsUseCase;

    //TODO just for test
    private GetUsersDetailsUseCase getUsersDetailsUseCase;
    private GetUsersByIdUseCase getMembersUseCase;


    public WalkPresenter(AuthManager authManager,
                         GetWalkUseCase getWalkUseCase,
                         EditWalkUseCase editWalkUseCase,
                         DeleteWalkUseCase deleteWalkUseCase,
                         GetUserDetailsUseCase getUserDetailsUseCase,
                         //GetUsersDetailsUseCase getUsersDetailsUseCase,
                         GetUsersByIdUseCase getMembersUseCase){
        this.authManager = authManager;
        this.getWalkUseCase = getWalkUseCase;
        this.editWalkUseCase = editWalkUseCase;
        this.deleteWalkUseCase = deleteWalkUseCase;
        this.getUserDetailsUseCase = getUserDetailsUseCase;
        //this.getUsersDetailsUseCase = getUsersDetailsUseCase;
        this.getMembersUseCase = getMembersUseCase;

        walkDtoModelMapper = new WalkDtoModelMapper();
        userDtoModelMapper = new UserDtoModelMapper();
    }

    public void setView(WalkDetailsView view){
        this.walkDetailsView = view;
    }

    public void initialize(String walkId){
        hideViewRetry();
        showViewLoading();
        //TODO change id
        getWalkDetails(authManager.getCurrentUserId(), walkId); //"ZtVM6D0rX6Vygni8NgyGz6kf9dB3" -MEx6IqCa4EEynKcz3gB -MEnwIuW7ATZ1CsiganB
    }

    public void getWalkDetails(String userId, String walkId){ //TODO изменить на walkID
        this.getWalkUseCase.execute(userId, walkId, new GetWalkUseCase.Callback() {
            @Override
            public void onWalkDataLoaded(WalkDto walkDto) {
                showWalkDetailInView(walkDto);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    public void getCreatorDetails(String userId){ // add List<String> membersId
        this.getUserDetailsUseCase.execute(userId, new GetUserDetailsUseCase.Callback() {
            @Override
            public void onUserDataLoaded(UserDto userDto) {
                showUserDetailsInView(userDto);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }



    public void getWalkMembersDetails(@NotNull List<String> membersId){
        this.getMembersUseCase.execute(membersId, new GetUsersByIdUseCase.Callback() {
            @Override
            public void onUsersDataLoaded(List<UserDto> users) {
                showWalkMembersInView(users);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }



    private void showWalkDetailInView(WalkDto walkDto){
        final WalkModel walkModel = this.walkDtoModelMapper.mapToModel(walkDto);
        this.walkDetailsView.renderCurrentWalk(walkModel);
    }

    private void showUserDetailsInView(UserDto userDto){
        final UserModel userModel = this.userDtoModelMapper.map2(userDto);
        this.walkDetailsView.renderWalkCreator(userModel);
    }

    private void showWalkMembersInView(List<UserDto> users){
        final List<UserModel> membersModel = this.userDtoModelMapper.fromDtoToModelList(users);
        this.walkDetailsView.renderWalkMembers(membersModel);
    }


    public void hideViewRetry(){
        this.walkDetailsView.hideLoading();
    }

    public void showViewLoading(){
        this.walkDetailsView.showLoading();
    }

    @Override
    public void onDestroy() {

    }
}
