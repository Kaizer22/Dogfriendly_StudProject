package com.lanit_tercom.domain.interactor.photo;

import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.Interactor;
import com.lanit_tercom.domain.repository.PhotoRepository;

public abstract class UseCase implements Interactor {
    protected final PhotoRepository photoRepository;
    protected final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    protected UseCase(PhotoRepository photoRepository,
                      ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        if (photoRepository == null || threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        this.photoRepository = photoRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected void execute() {
        this.threadExecutor.execute(this);
    }
}
