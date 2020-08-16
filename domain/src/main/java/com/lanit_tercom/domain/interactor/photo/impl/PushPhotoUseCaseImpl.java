package com.lanit_tercom.domain.interactor.photo.impl;

import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.photo.GetPhotoUseCase;
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase;
import com.lanit_tercom.domain.interactor.photo.UseCase;
import com.lanit_tercom.domain.repository.PhotoRepository;

public class PushPhotoUseCaseImpl extends UseCase implements PushPhotoUseCase {
    private PushPhotoUseCase.Callback callback;
    private String fileName;
    private String uriString;

    public PushPhotoUseCaseImpl(PhotoRepository photoRepository,
                               ThreadExecutor threadExecutor,
                               PostExecutionThread postExecutionThread) {
        super(photoRepository, threadExecutor, postExecutionThread);
    }

    public void execute(String fileName, String uriString, PushPhotoUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.fileName = fileName;
        this.uriString = uriString;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.photoRepository.pushPhoto(fileName, uriString, this.repositoryCallback);
    }

    private final PhotoRepository.PushPhotoCallback repositoryCallback =
            new PhotoRepository.PushPhotoCallback() {
                @Override
                public void onPhotoPushed() {
                    notifyPhotoPushedSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };



    private void notifyPhotoPushedSuccessfully() {
        this.postExecutionThread.post(() -> callback.onPhotoPushed());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }


}
