package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mapper.WalkDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.WalkModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.WalkDetailsView;
import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.walk.DeleteWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.EditWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.GetWalkUseCase;

public class WalkPresenter extends BasePresenter {

    private WalkDetailsView walkDetailsView;

    private AuthManager authManager;
    private WalkDtoModelMapper walkDtoModelMapper;

    private GetWalkUseCase getWalkUseCase;
    private EditWalkUseCase editWalkUseCase;
    private DeleteWalkUseCase deleteWalkUseCase;

    //TODO test ID
    private String walkId = "ZtVM6D0rX6Vygni8NgyGz6kf9dB3"; //ZtVM6D0rX6Vygni8NgyGz6kf9dB3 -MEnwIuW7ATZ1CsiganB


    public WalkPresenter(AuthManager authManager,
                         GetWalkUseCase getWalkUseCase,
                         EditWalkUseCase editWalkUseCase,
                         DeleteWalkUseCase deleteWalkUseCase){
        this.authManager = authManager;
        this.getWalkUseCase = getWalkUseCase;
        this.editWalkUseCase = editWalkUseCase;
        this.deleteWalkUseCase = deleteWalkUseCase;

        walkDtoModelMapper = new WalkDtoModelMapper();
    }

    public void setView(WalkDetailsView view){
        this.walkDetailsView = view;
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


    private void showWalkDetailInView(WalkDto walkDto){
        final WalkModel walkModel = this.walkDtoModelMapper.mapToModel(walkDto);
        this.walkDetailsView.renderCurrentWalk(walkModel);
    }

    public void initialize(){
        hideViewRetry();
        showViewLoading();
        //TODO test
        getWalkDetails("ZtVM6D0rX6Vygni8NgyGz6kf9dB3", "-MEnwIuW7ATZ1CsiganB");
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
