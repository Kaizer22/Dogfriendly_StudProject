package com.lanit_tercom.dogfriendly_studproject.data.firebase.walk;

import android.util.Log;

import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.WalkCache;
import com.lanit_tercom.library.data.manager.NetworkManager;

public class WalkEntityStoreFactory {

    private final NetworkManager networkManager;
    private final WalkCache walkCache;

    public WalkEntityStoreFactory(NetworkManager networkManager, WalkCache walkCache){
        if (networkManager == null){
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        this.networkManager = networkManager;
        this.walkCache = walkCache;
    }

    public WalkEntityStore create(){
        WalkEntityStore walkEntityStore;

        Log.d("Network", String.valueOf(networkManager.isNetworkAvailable()));
        walkEntityStore = new FirebaseWalkEntityStore(this.walkCache);

        return walkEntityStore;
    }
}
