package com.lanit_tercom.domain.interactor.channel;

import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.Interactor;
import com.lanit_tercom.domain.repository.ChannelRepository;

public abstract class UseCase implements Interactor {

    protected final ChannelRepository channelRepository;
    protected final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    protected UseCase(ChannelRepository channelRepository,
                      ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        if (channelRepository == null || threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        this.channelRepository = channelRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected void execute() {
        this.threadExecutor.execute(this);
    }
}
