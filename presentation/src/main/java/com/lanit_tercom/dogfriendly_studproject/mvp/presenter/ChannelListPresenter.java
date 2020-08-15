package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import android.util.Log;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mapper.ChannelModelDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChannelListView;
import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.DeleteChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase;
import com.lanit_tercom.domain.interactor.channel.impl.GetChannelsUseCaseImpl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class ChannelListPresenter extends BasePresenter {

    private ChannelListView channelListView;

    private AuthManager authManager;

    //private String userId;
    private List<ChannelModel> channelsList;

    private ChannelModelDtoMapper channelModelMapper;    /**final*/

    private GetChannelsUseCase getChannels;
    private AddChannelUseCase addChannel;
    private DeleteChannelUseCase deleteChannel;


    public ChannelListPresenter(AuthManager authManager){this.authManager = authManager;}

    public ChannelListPresenter(//String userId,
                                AuthManager authManager,
                                GetChannelsUseCase getChannelListUseCase,
                                AddChannelUseCase addChannelUseCase,
                                DeleteChannelUseCase deleteChannelUseCase){
        //this.userId = userId;
        this.authManager = authManager;
        this.getChannels = getChannelListUseCase;
        this.addChannel = addChannelUseCase;
        this.deleteChannel = deleteChannelUseCase;

        channelModelMapper = new ChannelModelDtoMapper();
        channelsList = new LinkedList<>();
    }

    public void setView(ChannelListView view){
        this.channelListView = view;
    }

    /*public void openChannel(ChannelModel channelModel){
        //TODO Открытые выбранного чата
    }*/


    public void deleteChannel(ChannelModel channelModel){
        ChannelDto channelDto = channelModelMapper.mapToDto(channelModel);
        deleteChannel.execute(authManager.getCurrentUserId(), channelDto, new DeleteChannelUseCase.Callback() {
            @Override
            public void onChannelDeleted() {
                //Действие после удалаения канала
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    public void turnOffNotifications(ChannelModel channelModel){
        //TODO Отключение уведомлений в выбранном диалоге
    }

    public void initialize(){
        loadChannelList();
    }

    public void loadChannelList(){
        this.hideViewRetry();
        this.showViewLoading();
        this.getChannelList();
    }

    public void hideViewRetry(){
        this.channelListView.hideLoading();
    }

    public void showViewLoading(){
        this.channelListView.showLoading();
    }

    private void showChannelListInView(List<ChannelDto> channelDtoList){
        final Collection<ChannelModel> channelModelList = this.channelModelMapper.transformList(channelDtoList);
        this.channelListView.renderChannels((List<ChannelModel>) channelModelList);
    }


    public void getChannelList(){
        //TODO authManager.getCurrentUserId() instead userId = 2345
        this.getChannels.execute("2345", new GetChannelsUseCase.Callback() {
            @Override
            public void onChannelsLoaded(List<ChannelDto> channels) {
                ChannelListPresenter.this.showChannelListInView(channels);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }


    /*public void onChannelClicked(ChannelModel channelModel){
        this.channelListView.viewChannel(channelModel);
    }*/

    public void refreshChannelsData(){
        getChannelList();
    }


    @Override
    public void onDestroy() {

    }
}
