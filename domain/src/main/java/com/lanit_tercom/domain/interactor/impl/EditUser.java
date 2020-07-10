package com.lanit_tercom.domain.interactor.impl;

import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.repository.Callback;
import com.lanit_tercom.domain.repository.UserRepository;

public class EditUser extends DetailsUseCaseImpl {
    private String userId = "";

    EditUser(UserRepository userRepository,
             ThreadExecutor threadExecutor,
             PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void run() {
        this.userRepository.editUser(this.userId, this.callback);
    }

    public void execute(String userId, Callback callback) {
        if (userId.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        this.userId = userId;
        super.perform(callback);
    }
}
