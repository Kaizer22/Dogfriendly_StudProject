package com.lanit_tercom.library.data.manager;

public interface NetworkManager {

    boolean isNetworkAvailable();

    void start();

    void stop();

    void add(String tag, Listener listener);

    void remove(String tag);

    interface Listener{
        void onNetworkAvailable();
    }
}
