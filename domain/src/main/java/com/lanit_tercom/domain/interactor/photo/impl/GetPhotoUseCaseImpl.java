package com.lanit_tercom.domain.interactor.photo.impl;

import com.lanit_tercom.domain.dto.PetDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.photo.GetPhotoUseCase;
import com.lanit_tercom.domain.interactor.photo.UseCase;
import com.lanit_tercom.domain.interactor.user.AddPetUseCase;
import com.lanit_tercom.domain.repository.PhotoRepository;
import com.lanit_tercom.domain.repository.UserRepository;

public class GetPhotoUseCaseImpl extends UseCase implements GetPhotoUseCase {
    private GetPhotoUseCase.Callback callback;
    private String fileName;

    public GetPhotoUseCaseImpl(PhotoRepository photoRepository,
                               ThreadExecutor threadExecutor,
                               PostExecutionThread postExecutionThread) {
        super(photoRepository, threadExecutor, postExecutionThread);
    }

    public void execute(String fileName, GetPhotoUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.fileName = fileName;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.photoRepository.getPhoto(fileName, this.repositoryCallback);
    }

    private final PhotoRepository.GetPhotoCallback repositoryCallback =
            new PhotoRepository.GetPhotoCallback() {
                @Override
                public void onPhotoLoaded(String uriString) {
                    notifyPhotoLoadedSuccessfully(uriString);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };



    private void notifyPhotoLoadedSuccessfully(final String uriString) {
        this.postExecutionThread.post(() -> callback.onPhotoLoaded(uriString));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}
