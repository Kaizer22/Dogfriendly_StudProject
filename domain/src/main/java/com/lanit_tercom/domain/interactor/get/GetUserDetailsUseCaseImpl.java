package com.lanit_tercom.domain.interactor.get;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.UseCase;
import com.lanit_tercom.domain.repository.UserRepository;

public class GetUserDetailsUseCaseImpl extends UseCase implements GetUserDetailsUseCase {

    private String userId = "";
    private GetUserDetailsUseCase.Callback callback;

    public GetUserDetailsUseCaseImpl(UserRepository userRepository,
                                     ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(String userId, Callback callback) {
        if (userId.isEmpty() || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.callback = callback;
        this.userId = userId;
    }

    @Override
    public void run() {
        this.userRepository.getUserById(this.userId, this.repositoryCallback);
    }

    private final UserRepository.UserDetailsCallback repositoryCallback =
            new UserRepository.UserDetailsCallback() {
                @Override
                public void onUserLoaded(UserDto userDto) {
                    notifyGetUserDetailsSuccessfully(userDto);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetUserDetailsSuccessfully(final UserDto userDto) {
        this.postExecutionThread.post(() -> callback.onUserDataLoaded(userDto));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}