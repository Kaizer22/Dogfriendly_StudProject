package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelListModel;

import java.util.ArrayList;
import java.util.List;

public class ChannelsProviderTemp {

    public static List<ChannelListModel> getChannels(){
        List<ChannelListModel> channels = new ArrayList<>();
        channels.add(new ChannelListModel("1", "ID - Alex", "hey", "7h"));
        channels.add(new ChannelListModel("2", "ID - Thomas", ":)?!?!?!?!", "1d"));
        channels.add(new ChannelListModel("3", "ID - Fill", "ahahahah", "3d"));
        channels.add(new ChannelListModel("4", "ID - Inna", "1234", "1m"));
        channels.add(new ChannelListModel("4", "ID - Sofia", "1234", "1m"));
        channels.add(new ChannelListModel("4", "ID - Jastin", "1234", "1m"));

        return channels;
    }
}
