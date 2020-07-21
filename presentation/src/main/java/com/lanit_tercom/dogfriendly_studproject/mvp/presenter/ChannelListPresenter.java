package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.mapper.ChannelModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChannelListView;
import com.lanit_tercom.domain.interactor.get.GetChannelListImpl;


public class ChannelListPresenter extends BasePresenter {


    private ChannelListView channelListView;

    private  GetChannelListImpl getChannelListUseCase; /**final*/
    private  ChannelModelMapper channelModelMapper;    /**final*/

    public ChannelListPresenter(){}

    public ChannelListPresenter(GetChannelListImpl getChannelListUseCase,
                                ChannelModelMapper channelModelMapper){
        this.getChannelListUseCase = getChannelListUseCase;
        this.channelModelMapper = channelModelMapper;

    }

    public void setView(ChannelListView view){
        this.channelListView = view;
    }

    public void openDialog(ChannelModel channelModel){
        //TODO Открытые выбранного диалога
    }

    public void deleteDialog(ChannelModel channelModel){
        //TODO Удаление выбранного диалога
    }

    public void turnOffNotifications(ChannelModel channelModel){
        //TODO Отключение уведомлений в выбранном диалоге
    }

    private void loadChannelList(){
        this.hideViewRetry();
        this.showViewLoading();
        this.getChannelList();
    }

    private void hideViewRetry(){
        this.channelListView.hideLoading();
    }

    private void showViewLoading(){
        this.channelListView.showLoading();
    }

    /**
     * how get channelID ???
     */
    private void getChannelList(){
        this.getChannelListUseCase.execute("",null);
    }



}
