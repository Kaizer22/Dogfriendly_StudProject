package com.lanit_tercom.domain.interactor.walk;

import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.Interactor;
import com.lanit_tercom.domain.repository.ChannelRepository;
import com.lanit_tercom.domain.repository.WalkRepository;

public abstract class UseCase implements Interactor {

    protected final WalkRepository walkRepository;
    protected final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;


    protected UseCase(WalkRepository walkRepository,
                      ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        if (walkRepository == null || threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        this.walkRepository = walkRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }


    protected void execute() {
        this.threadExecutor.execute(this);
    }

}
