package com.lanit_tercom.domain.interactor.user.impl;

import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.user.DeleteUserDetailUseCase;
import com.lanit_tercom.domain.interactor.user.UseCase;
import com.lanit_tercom.domain.repository.UserRepository;

public class DeleteUserDetailUseCaseImpl extends UseCase implements DeleteUserDetailUseCase {
    private DeleteUserDetailUseCase.Callback callback;
    private String id;

    public DeleteUserDetailUseCaseImpl(UserRepository userRepository,
                                        ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }

    public void execute(String id, DeleteUserDetailUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.id = id;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.userRepository.deleteUser(id, this.repositoryCallback);
    }

    private final UserRepository.UserDeleteCallback repositoryCallback =
            new UserRepository.UserDeleteCallback() {

                @Override
                public void onUserDeleted() {
                    notifyDeleteUserDetailsSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyDeleteUserDetailsSuccessfully() {
        this.postExecutionThread.post(() -> callback.onUserDeleted());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }

}
