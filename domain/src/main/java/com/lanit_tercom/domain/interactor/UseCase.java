package com.lanit_tercom.domain.interactor;

import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.repository.UserRepository;

public abstract class UseCase implements Interactor {

    protected final UserRepository userRepository;
    protected final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    protected UseCase(UserRepository userRepository,
                      ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        if (userRepository == null || threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        this.userRepository = userRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected void execute() {
        this.threadExecutor.execute(this);
    }
}