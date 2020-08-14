package com.lanit_tercom.domain.interactor.user.impl;

import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.user.UseCase;
import com.lanit_tercom.domain.interactor.user.DeletePetUseCase;
import com.lanit_tercom.domain.repository.UserRepository;

public class DeletePetUseCaseImpl extends UseCase implements DeletePetUseCase {
    private DeletePetUseCase.Callback callback;
    private String userId;
    private String petId;

    public DeletePetUseCaseImpl(UserRepository userRepository,
                             ThreadExecutor threadExecutor,
                             PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }

    public void execute(String userId, String petId, DeletePetUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.userId= userId;
        this.petId = petId;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.userRepository.deletePet(userId, petId, this.repositoryCallback);
    }

    private final UserRepository.DeletePetCallback repositoryCallback =
            new UserRepository.DeletePetCallback() {

                @Override
                public void onPetDeleted() {
                    notifyDeletePetSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyDeletePetSuccessfully() {
        this.postExecutionThread.post(() -> callback.onPetDeleted());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }

}
