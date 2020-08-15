package com.lanit_tercom.dogfriendly_studproject.data.firebase.channel;

import android.util.Log;

import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.ChannelCache;
import com.lanit_tercom.library.data.manager.NetworkManager;

public class ChannelEntityStoreFactory {

    private final NetworkManager networkManager;
    private final ChannelCache channelCache;

    public ChannelEntityStoreFactory(NetworkManager networkManager, ChannelCache channelCache){
        if (networkManager == null){
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        this.networkManager = networkManager;
        this.channelCache = channelCache;
    }

    public ChannelEntityStore create(){
        ChannelEntityStore userEntityStore;

        Log.d("Network", String.valueOf(networkManager.isNetworkAvailable()));

        userEntityStore = new FirebaseChannelEntityStore(this.channelCache);

//        if (networkManager.isNetworkAvailable()) {
//            userEntityStore = new FirebaseUserEntityStore(this.userCache);
//        } else {
//            userEntityStore = userCache;
//        }

        return userEntityStore;
    }

}
