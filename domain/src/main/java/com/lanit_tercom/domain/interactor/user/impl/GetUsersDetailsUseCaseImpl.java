package com.lanit_tercom.domain.interactor.user.impl;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.UseCase;
import com.lanit_tercom.domain.interactor.user.GetUsersDetailsUseCase;
import com.lanit_tercom.domain.repository.UserRepository;

import java.util.List;

public class GetUsersDetailsUseCaseImpl extends UseCase implements GetUsersDetailsUseCase {

    private GetUsersDetailsUseCase.Callback callback;

    public GetUsersDetailsUseCaseImpl(UserRepository userRepository,
                                      ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.callback = callback;
    }

    @Override
    public void run() {
        this.userRepository.getUsers(this.repositoryCallback);
    }

    private final UserRepository.UsersDetailsCallback repositoryCallback =
            new UserRepository.UsersDetailsCallback() {
                @Override
                public void onUsersLoaded(List<UserDto> userDto) {
                    notifyGetUsersSuccessfully(userDto);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetUsersSuccessfully(final List<UserDto> users) {
        this.postExecutionThread.post(() -> callback.onUsersDataLoaded(users));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}