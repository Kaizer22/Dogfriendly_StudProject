package com.lanit_tercom.dogfriendly_studproject.mvp.view;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;

import java.util.List;

public interface ChannelListView extends LoadDataView{

    /*
        Показать пользователю, что диалог имеет непрочитанные сообщения.
     */
    void showUnreadMessage();

    void renderChannels(List<ChannelModel> channels);
}
