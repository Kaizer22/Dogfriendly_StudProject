package com.lanit_tercom.dogfriendly_studproject.data.firebase.photo;

import android.util.Log;

import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.MessageCache;
import com.lanit_tercom.library.data.manager.NetworkManager;

public class PhotoStoreFactory {
    private final NetworkManager networkManager;

    public PhotoStoreFactory(NetworkManager networkManager) {
        if (networkManager == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        this.networkManager = networkManager;
    }

    public PhotoStore create() {
        PhotoStore photoStore;

        Log.d("Network", String.valueOf(networkManager.isNetworkAvailable()));

        photoStore = new FirebasePhotoStore();

//        if (networkManager.isNetworkAvailable()) {
//            messageEntityStore = new FirebaseMessageEntityStore(this.messageCache);
//        } else {
//            messageEntityStore = messageCache;
//        }

        return photoStore;
    }
}
