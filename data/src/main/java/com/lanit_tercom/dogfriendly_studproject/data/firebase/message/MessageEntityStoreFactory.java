package com.lanit_tercom.dogfriendly_studproject.data.firebase.message;

import android.util.Log;

import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.MessageCache;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.FirebaseMessageEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.MessageEntityStore;
import com.lanit_tercom.library.data.manager.NetworkManager;

public class MessageEntityStoreFactory {
    private final NetworkManager networkManager;
    private final MessageCache messageCache;

    public MessageEntityStoreFactory(NetworkManager networkManager, MessageCache messageCache) {
        if (networkManager == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        this.networkManager = networkManager;
        this.messageCache = messageCache;
    }

    public MessageEntityStore create() {
        MessageEntityStore messageEntityStore;

        Log.d("Network", String.valueOf(networkManager.isNetworkAvailable()));

        messageEntityStore = new FirebaseMessageEntityStore(this.messageCache);
//        if (networkManager.isNetworkAvailable()) {
//            messageEntityStore = new FirebaseMessageEntityStore(this.messageCache);
//        } else {
//            messageEntityStore = messageCache;
//        }

        return messageEntityStore;
    }
}
