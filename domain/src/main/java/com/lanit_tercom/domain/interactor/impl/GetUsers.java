package com.lanit_tercom.domain.interactor.impl;

import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.repository.UserRepository;

public class GetUsers extends DetailsUseCaseImpl {
    public GetUsers(UserRepository userRepository,
                    ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void run() {
        userRepository.getUsers(this.callback);
    }
}
