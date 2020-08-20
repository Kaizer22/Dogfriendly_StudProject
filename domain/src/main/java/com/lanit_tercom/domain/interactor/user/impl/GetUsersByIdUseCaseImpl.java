package com.lanit_tercom.domain.interactor.user.impl;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.user.GetUsersByIdUseCase;
import com.lanit_tercom.domain.interactor.user.GetUsersDetailsUseCase;
import com.lanit_tercom.domain.interactor.user.UseCase;
import com.lanit_tercom.domain.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;

public class GetUsersByIdUseCaseImpl extends UseCase implements GetUsersByIdUseCase {

    private List<String> usersId = new LinkedList<>();
    private GetUsersByIdUseCase.Callback callback;

    public GetUsersByIdUseCaseImpl(UserRepository userRepository,
                                   ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }


    @Override
    public void execute(List<String> usersId, Callback callback) {
        if (usersId.isEmpty() || callback == null){
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.usersId = usersId;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.userRepository.getUserListById(usersId, this.repositoryCallback);
    }

    private final UserRepository.UsersListDetailsCallback repositoryCallback =
            new UserRepository.UsersListDetailsCallback() {
                @Override
                public void onUsersListLoaded(List<UserDto> users) {
                    notifyGetUserDetailsSuccessfully(users);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetUserDetailsSuccessfully(final List<UserDto> users) {
        this.postExecutionThread.post(() -> callback.onUsersDataLoaded(users));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}
