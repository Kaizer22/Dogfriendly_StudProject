package com.lanit_tercom.domain.interactor.user.create;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.user.UseCase;
import com.lanit_tercom.domain.repository.UserRepository;

public class CreateUserDetailsUseCaseImpl extends UseCase implements CreateUserDetailsUseCase {

    private CreateUserDetailsUseCase.Callback callback;

    public CreateUserDetailsUseCaseImpl(UserRepository userRepository,
                                        ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }

    public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.callback = callback;
    }

    @Override
    public void run() {
        this.userRepository.createUser(this.repositoryCallback);
    }

    private final UserRepository.UserDetailsCallback repositoryCallback =
            new UserRepository.UserDetailsCallback() {
                @Override
                public void onUserLoaded(UserDto userDto) {
                    notifyCreateUserDetailsSuccessfully(userDto);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyCreateUserDetailsSuccessfully(final UserDto userDto) {
        this.postExecutionThread.post(() -> callback.onUserDataCreated(userDto));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}