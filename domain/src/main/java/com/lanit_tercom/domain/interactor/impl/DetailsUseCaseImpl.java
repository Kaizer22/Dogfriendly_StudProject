package com.lanit_tercom.domain.interactor.impl;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.DetailsUseCase;
import com.lanit_tercom.domain.repository.Callback;
import com.lanit_tercom.domain.repository.UserRepository;

public abstract class DetailsUseCaseImpl implements DetailsUseCase {

    Callback callback;

    final UserRepository userRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    DetailsUseCaseImpl(UserRepository userRepository,
                       ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        if (userRepository == null || threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        this.userRepository = userRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void perform(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    private void notifyUserDetailsSuccessfully(final UserDto userDto) {
        this.postExecutionThread.post(() ->
                callback.onUserLoaded(userDto));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() ->
                callback.onError(errorBundle));
    }
}