package com.lanit_tercom.domain.interactor.user.impl;

import com.lanit_tercom.domain.dto.PetDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.user.AddPetUseCase;
import com.lanit_tercom.domain.interactor.user.DeleteUserDetailUseCase;
import com.lanit_tercom.domain.interactor.user.UseCase;
import com.lanit_tercom.domain.repository.UserRepository;

public class AddPetUseCaseImpl extends UseCase implements AddPetUseCase{
    private AddPetUseCase.Callback callback;
    private String id;
    private PetDto petDto;

    public AddPetUseCaseImpl(UserRepository userRepository,
                                       ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }

    public void execute(String id, PetDto petDto, AddPetUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.id = id;
        this.petDto = petDto;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.userRepository.addPet(id, petDto, this.repositoryCallback);
    }

    private final UserRepository.AddPetCallback repositoryCallback =
            new UserRepository.AddPetCallback() {

                @Override
                public void onPetAdded() {
                    notifyAddPetSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyAddPetSuccessfully() {
        this.postExecutionThread.post(() -> callback.onPetAdded());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}
