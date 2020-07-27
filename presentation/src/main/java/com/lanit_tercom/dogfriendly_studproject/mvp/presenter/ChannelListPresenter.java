package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import android.util.Log;

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

    private String userId;
    private List<ChannelModel> channelsList;

    private ChannelModelDtoMapper channelModelMapper;    /**final*/

    private GetChannelsUseCase getChannels;
    private AddChannelUseCase addChannel;
    private DeleteChannelUseCase deleteChannel;


    //public ChannelListPresenter(){}

    public ChannelListPresenter(String userId,
                                GetChannelsUseCase getChannelListUseCase,
                                AddChannelUseCase addChannelUseCase,
                                DeleteChannelUseCase deleteChannelUseCase){
        this.userId = userId;
        this.getChannels = getChannelListUseCase;
        this.addChannel = addChannelUseCase;
        this.deleteChannel = deleteChannelUseCase;

        channelModelMapper = new ChannelModelDtoMapper();
        channelsList = new LinkedList<>();
    }

    public void setView(ChannelListView view){
        this.channelListView = view;
    }

    public void openChannel(ChannelModel channelModel){
        //TODO Открытые выбранного чата
    }


    public void deleteChannel(ChannelModel channelModel){
        ChannelDto channelDto = channelModelMapper.mapToDto(channelModel);
        deleteChannel.execute(userId, channelDto, new DeleteChannelUseCase.Callback() {
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

//    public List<ChannelModel> getChannelList(){
//        return channelsList;
//    }

    public void getChannelList(){
        this.getChannels.execute(userId, new GetChannelsUseCase.Callback() {
            @Override
            public void onChannelsLoaded(List<ChannelDto> channels) {
               /* for(ChannelDto channelDto: channels){
                    channelsList.add(channelModelMapper.mapToModel(channelDto));
                }
                channelListView.renderChannels(channelsList);
                channelListView.hideLoading();*/
                Log.e("ChannelDto", "channels:");
                System.out.println(channels.toString());
                ChannelListPresenter.this.showChannelListInView(channels);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }



    public void refreshChannelsData(){
        getChannelList();
    }


}
