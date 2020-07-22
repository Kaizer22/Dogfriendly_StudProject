package com.lanit_tercom.domain.interactor.user.impl;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.user.CreateUserDetailsUseCase;
import com.lanit_tercom.domain.interactor.user.UseCase;
import com.lanit_tercom.domain.repository.UserRepository;

public class CreateUserDetailsUseCaseImpl extends UseCase implements CreateUserDetailsUseCase {

    private CreateUserDetailsUseCase.Callback callback;
    private UserDto userDto;

    public CreateUserDetailsUseCaseImpl(UserRepository userRepository,
                                        ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }

    public void execute(UserDto userDto, Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.userDto = userDto;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.userRepository.createUser(userDto, this.repositoryCallback);
    }

    private final UserRepository.UserCreateCallback repositoryCallback =
            new UserRepository.UserCreateCallback() {
                @Override
                public void onUserCreated() {
                    notifyCreateUserDetailsSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyCreateUserDetailsSuccessfully() {
        this.postExecutionThread.post(() -> callback.onUserDataCreated());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}