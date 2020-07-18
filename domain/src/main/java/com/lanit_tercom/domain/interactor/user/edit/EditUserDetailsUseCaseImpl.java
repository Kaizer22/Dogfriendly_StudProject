package com.lanit_tercom.domain.interactor.user.edit;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.user.UseCase;
import com.lanit_tercom.domain.repository.UserRepository;

public class EditUserDetailsUseCaseImpl extends UseCase implements EditUserDetailsUseCase {

    private String userId = "";
    private EditUserDetailsUseCase.Callback callback;

    public EditUserDetailsUseCaseImpl(UserRepository userRepository,
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
        this.userRepository.editUserById(this.userId, this.repositoryCallback);
    }

    private final UserRepository.UserDetailsCallback repositoryCallback =
            new UserRepository.UserDetailsCallback() {
                @Override
                public void onUserLoaded(UserDto userDto) {
                    notifyEditUserDetailsSuccessfully(userDto);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyEditUserDetailsSuccessfully(final UserDto userDto) {
        this.postExecutionThread.post(() -> callback.onUserDataEdited(userDto));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}