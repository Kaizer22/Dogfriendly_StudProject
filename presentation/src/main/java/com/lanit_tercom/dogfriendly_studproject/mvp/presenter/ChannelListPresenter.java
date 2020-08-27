package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;
import com.lanit_tercom.dogfriendly_studproject.mapper.ChannelDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mapper.MessageDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChannelListView;
import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.DeleteChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.EditChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase;
import com.lanit_tercom.domain.interactor.message.GetLastMessagesUseCase;
import com.lanit_tercom.domain.interactor.message.GetMessagesUseCase;
import com.lanit_tercom.domain.interactor.user.GetUsersByIdUseCase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ChannelListPresenter extends BasePresenter {

    private ChannelListView channelListView;

    List<MessageModel> messages = new ArrayList<>();
    List<ChannelModel> channels = new ArrayList<>();
    List<UserModel> channelMembersList = new ArrayList<>();
    String currentUserID = new AuthManagerFirebaseImpl().getCurrentUserId();

    private AuthManager authManager;
    private ChannelDtoModelMapper channelModelMapper;
    private UserDtoModelMapper userModelMapper;
    private MessageDtoModelMapper messageDtoModelMapper;
    private boolean isChannelListEmpty;

    private GetChannelsUseCase getChannels;
    private AddChannelUseCase addChannel;
    private EditChannelUseCase editChannel;
    private DeleteChannelUseCase deleteChannel;
    private GetUsersByIdUseCase getUsersByIdUseCase;
    private GetLastMessagesUseCase getLastMessagesUseCase;

    public ChannelListPresenter(AuthManager authManager,
                                GetChannelsUseCase getChannelListUseCase,
                                AddChannelUseCase addChannelUseCase,
                                EditChannelUseCase editChannelUseCase,
                                DeleteChannelUseCase deleteChannelUseCase,
                                GetUsersByIdUseCase getUsersByIdUseCase,
                                GetLastMessagesUseCase getLastMessagesUseCase){
        this.authManager = authManager;
        this.getChannels = getChannelListUseCase;
        this.addChannel = addChannelUseCase;
        this.editChannel = editChannelUseCase;
        this.deleteChannel = deleteChannelUseCase;
        this.getUsersByIdUseCase = getUsersByIdUseCase;
        this.getLastMessagesUseCase = getLastMessagesUseCase;

        channelModelMapper = new ChannelDtoModelMapper();
        userModelMapper = new UserDtoModelMapper();
        messageDtoModelMapper = new MessageDtoModelMapper();
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

    public void getLastMessageDetails(List<String> channelsId){
        this.getLastMessagesUseCase.execute(channelsId, new GetLastMessagesUseCase.Callback() {
            @Override
            public void onLastMessagesLoaded(List<MessageDto> messages) {
                ChannelListPresenter.this.showLastMessageInView(messages);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                errorBundle.getException().printStackTrace();
            }
        });
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
        List<String> channelsId = new ArrayList<>();
        List<String> receiversId = new ArrayList<>();
        final List<ChannelModel> channelModelList = this.channelModelMapper.transformList(channelDtoList);

        for (ChannelModel channel: channelModelList){
            channelsId.add(channel.getId());
            for(String id: channel.getMembers()){
                if (!id.equals(currentUserID)){
                    receiversId.add(id);
                }
            }
        }
        this.getLastMessageDetails(channelsId);
        this.getChannelMembers(receiversId);
        channels = channelModelList;
    }

    private void sendChannelMembersInView(List<UserDto> channelMembers){
        channelMembersList = this.userModelMapper.fromDtoToModelList(channelMembers);
        this.channelListView.renderChannelMembers(channelMembersList);
    }

    private void showLastMessageInView(@NotNull List<MessageDto> messageDtoList){
        messages = this.messageDtoModelMapper.map2(messageDtoList);
        this.channelListView.renderChannels(channels, messages);
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
