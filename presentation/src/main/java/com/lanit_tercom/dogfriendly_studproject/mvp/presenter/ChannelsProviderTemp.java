package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;

import java.util.ArrayList;
import java.util.List;

public class ChannelsProviderTemp {

    public static List<ChannelModel> getChannels(){
        List<ChannelModel> channels = new ArrayList<>();
        channels.add(new ChannelModel("1", "hey", "ID - Alex", "7h"));
        channels.add(new ChannelModel("2", ":)?!?!?!?!", "ID - Thomas", "1d"));
        channels.add(new ChannelModel("3", "ahahahah", "ID - Fill", "3d"));
        channels.add(new ChannelModel("4", "1234", "ID - Inna", "1m"));
        channels.add(new ChannelModel("4", "how a u", "ID - Sofia", "1m"));
        channels.add(new ChannelModel("4", "hey man", "ID - Jastin", "1m"));
        channels.add(new ChannelModel("1", "I'm' Alex", "ID - AlexT", "7h"));


        return channels;
    }
}
