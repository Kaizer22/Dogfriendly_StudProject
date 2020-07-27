package com.lanit_tercom.domain.interactor.user.impl;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase;
import com.lanit_tercom.domain.interactor.user.UseCase;
import com.lanit_tercom.domain.repository.UserRepository;

public class EditUserDetailsUseCaseImpl extends UseCase implements EditUserDetailsUseCase {

    private UserDto userDto;
    private EditUserDetailsUseCase.Callback callback;

    public EditUserDetailsUseCaseImpl(UserRepository userRepository,
                                      ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }

    @Override
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
        this.userRepository.editUser(this.userDto, this.repositoryCallback);
    }

    private final UserRepository.UserEditCallback repositoryCallback =
            new UserRepository.UserEditCallback() {
                @Override
                public void onUserEdited() {
                    notifyEditUserDetailsSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyEditUserDetailsSuccessfully() {
        this.postExecutionThread.post(() -> callback.onUserDataEdited());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}