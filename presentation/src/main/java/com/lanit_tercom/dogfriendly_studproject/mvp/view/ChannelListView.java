package com.lanit_tercom.dogfriendly_studproject.mvp.view;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel;

import java.util.List;

public interface ChannelListView extends LoadDataView{

    /*
        Показать пользователю, что диалог имеет непрочитанные сообщения.
     */
    void showUnreadMessage();

    void renderChannels(List<ChannelModel> channels, List<MessageModel> messages);

    void renderChannelMembers(List<UserModel> channelMembers);

    void renderChannelLastMessage(MessageModel messageModel);

    /**
     * View a {@link ChannelModel} profile/details.
     *
     * @param channelModel The channel that will be shown.
     */
    //void viewChannel(ChannelModel channelModel);
}
