package com.lanit_tercom.dogfriendly_studproject.executor;

import android.os.Handler;
import android.os.Looper;

import com.lanit_tercom.domain.executor.PostExecutionThread;


public class UIThread implements PostExecutionThread {

    private static class LazyHolder {
        private static final UIThread INSTANCE = new UIThread();
    }

    public static UIThread getInstance() {
        return LazyHolder.INSTANCE;
    }

    private final Handler handler;

    private UIThread() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override public void post(Runnable runnable) {
        handler.post(runnable);
    }
}

