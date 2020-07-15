package com.lanit_tercom.dogfriendly_studproject.data.firebase;

import android.util.Log;

import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.UserCache;
import com.lanit_tercom.library.data.manager.NetworkManager;

public class UserEntityStoreFactory {

    private final NetworkManager networkManager;
    private final UserCache userCache;

    public UserEntityStoreFactory(NetworkManager networkManager, UserCache userCache){
        if (networkManager == null){
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        this.networkManager = networkManager;
        this.userCache = userCache;
    }

    public UserEntityStore create(){
        UserEntityStore userEntityStore;

        Log.d("Network", String.valueOf(networkManager.isNetworkAvailable()));

        userEntityStore = new FirebaseUserEntityStore(this.userCache);
//        if (networkManager.isNetworkAvailable()) {
//            userEntityStore = new FirebaseUserEntityStore(this.userCache);
//        } else {
//            userEntityStore = userCache;
//        }

        return userEntityStore;

    }
}