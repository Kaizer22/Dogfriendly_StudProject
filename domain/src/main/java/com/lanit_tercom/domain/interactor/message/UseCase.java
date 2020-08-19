package com.lanit_tercom.domain.interactor.message;

import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.Interactor;
import com.lanit_tercom.domain.repository.MessageRepository;

public abstract class UseCase implements Interactor {
    protected final MessageRepository messageRepository;
    protected final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    protected UseCase(MessageRepository messageRepository,
                      ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        if (messageRepository == null || threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        this.messageRepository = messageRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected void execute() {
        this.threadExecutor.execute(this);
    }
}