package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;

import java.util.ArrayList;
import java.util.List;

public class ChannelsProviderTemp {

    public static List<ChannelModel> getChannels(){
        List<ChannelModel> channels = new ArrayList<>();
        channels.add(new ChannelModel("1", "ID - Alex", "hey", "7h"));
        channels.add(new ChannelModel("2", "ID - Thomas", ":)?!?!?!?!", "1d"));
        channels.add(new ChannelModel("3", "ID - Fill", "ahahahah", "3d"));
        channels.add(new ChannelModel("4", "ID - Inna", "1234", "1m"));
        channels.add(new ChannelModel("4", "ID - Sofia", "1234", "1m"));
        channels.add(new ChannelModel("4", "ID - Jastin", "1234", "1m"));

        return channels;
    }
}
