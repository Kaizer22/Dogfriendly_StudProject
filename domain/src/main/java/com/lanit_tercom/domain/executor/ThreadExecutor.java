package com.lanit_tercom.domain.executor;

public interface ThreadExecutor {

    /**
     * Executes a {@link Runnable}.
     *
     * @param runnable The class that implements {@link Runnable} interface.
     */
    void execute(final Runnable runnable);
}
