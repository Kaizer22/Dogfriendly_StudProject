package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mapper.ChannelDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChannelListView;
import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.DeleteChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.EditChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase;
import com.lanit_tercom.domain.interactor.user.GetUsersByIdUseCase;

import java.util.Collection;
import java.util.List;


public class ChannelListPresenter extends BasePresenter {

    private ChannelListView channelListView;

    private AuthManager authManager;
    private ChannelDtoModelMapper channelModelMapper;
    private UserDtoModelMapper userModelMapper;
    private boolean isChannelListEmpty;

    private GetChannelsUseCase getChannels;
    private AddChannelUseCase addChannel;
    private EditChannelUseCase editChannel;
    private DeleteChannelUseCase deleteChannel;
    private GetUsersByIdUseCase getUsersByIdUseCase;

    public ChannelListPresenter(AuthManager authManager,
                                GetChannelsUseCase getChannelListUseCase,
                                AddChannelUseCase addChannelUseCase,
                                EditChannelUseCase editChannelUseCase,
                                DeleteChannelUseCase deleteChannelUseCase,
                                GetUsersByIdUseCase getUsersByIdUseCase){
        this.authManager = authManager;
        this.getChannels = getChannelListUseCase;
        this.addChannel = addChannelUseCase;
        this.editChannel = editChannelUseCase;
        this.deleteChannel = deleteChannelUseCase;
        this.getUsersByIdUseCase = getUsersByIdUseCase;

        channelModelMapper = new ChannelDtoModelMapper();
        userModelMapper = new UserDtoModelMapper();
    }

    public void setView(ChannelListView view){
        this.channelListView = view;
    }


    public void deleteChannel(ChannelModel channelModel){
        ChannelDto channelDto = channelModelMapper.mapToDto(channelModel);
        deleteChannel.execute(authManager.getCurrentUserId(), channelDto, new DeleteChannelUseCase.Callback() {
            @Override
            public void onChannelDeleted() {
                refreshChannelsData();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    public void getChannelList(){
        this.getChannels.execute(authManager.getCurrentUserId(), new GetChannelsUseCase.Callback() {
            @Override
            public void onChannelsLoaded(List<ChannelDto> channels) {
                ChannelListPresenter.this.showChannelListInView(channels);
                isChannelListEmpty = channels.size() == 0;
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    public void getChannelMembers(List<String> membersId){
        this.getUsersByIdUseCase.execute(membersId, new GetUsersByIdUseCase.Callback() {
            @Override
            public void onUsersDataLoaded(List<UserDto> users) {
                ChannelListPresenter.this.sendChannelMembersInView(users);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
    }

    public void editChannel(ChannelModel channelModel){

        ChannelDto channelDto = channelModelMapper.mapToDto(channelModel);
        this.editChannel.execute(channelDto, new EditChannelUseCase.Callback() {
            @Override
            public void onChannelEdited() {
                refreshChannelsData();
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

    private void sendChannelMembersInView(List<UserDto> channelMembers){
        final List<UserModel> userModelList = this.userModelMapper.fromDtoToModelList(channelMembers);
        this.channelListView.renderChannelMembers(userModelList);
    }


    public void refreshChannelsData(){
        getChannelList();
    }

    public boolean isChannelListEmpty(){
        return isChannelListEmpty;
    }

    @Override
    public void onDestroy() {
        this.channelListView = null;
    }
}
