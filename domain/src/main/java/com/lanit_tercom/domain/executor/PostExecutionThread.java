package com.lanit_tercom.domain.executor;

public interface PostExecutionThread {
    void post(Runnable runnable);
}
