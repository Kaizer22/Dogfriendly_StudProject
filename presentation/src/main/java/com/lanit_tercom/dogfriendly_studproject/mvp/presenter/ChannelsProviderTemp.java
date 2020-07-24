package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import android.content.ContentProvider;

import androidx.annotation.UiThread;
import androidx.core.content.ContextCompat;

import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.ui.activity.ChannelListActivity;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.channel.impl.GetChannelsUseCaseImpl;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;

import java.util.ArrayList;
import java.util.List;

public class ChannelsProviderTemp {

    public static List<ChannelModel> getChannels(){
        List<ChannelModel> channels = new ArrayList<>();

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        /*
        channels.add(new ChannelModel("1", "hey", "ID - Alex", "7h"));
        channels.add(new ChannelModel("2", ":)?!?!?!?!", "ID - Thomas", "1d"));
        channels.add(new ChannelModel("3", "ahahahah", "ID - Fill", "3d"));
        channels.add(new ChannelModel("4", "1234", "ID - Inna", "1m"));
        channels.add(new ChannelModel("4", "how a u", "ID - Sofia", "1m"));
        channels.add(new ChannelModel("4", "hey man", "ID - Jastin", "1m"));
        channels.add(new ChannelModel("1", "I'm' Alex", "ID - AlexT", "7h"));*/


        return channels;
    }
}
