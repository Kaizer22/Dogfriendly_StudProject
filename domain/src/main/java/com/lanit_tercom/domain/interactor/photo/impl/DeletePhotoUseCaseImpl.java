package com.lanit_tercom.domain.interactor.photo.impl;

import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase;
import com.lanit_tercom.domain.interactor.photo.UseCase;
import com.lanit_tercom.domain.repository.PhotoRepository;

public class DeletePhotoUseCaseImpl extends UseCase implements DeletePhotoUseCase {
    private DeletePhotoUseCase.Callback callback;
    private String fileName;


    public DeletePhotoUseCaseImpl(PhotoRepository photoRepository,
                                ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread) {
        super(photoRepository, threadExecutor, postExecutionThread);
    }

    public void execute(String fileName, DeletePhotoUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.fileName = fileName;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.photoRepository.deletePhoto(fileName, this.repositoryCallback);
    }

    private final PhotoRepository.DeletePhotoCallback repositoryCallback =
            new PhotoRepository.DeletePhotoCallback() {
                @Override
                public void onPhotoDeleted() {
                    notifyPhotoDeletedSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };



    private void notifyPhotoDeletedSuccessfully() {
        this.postExecutionThread.post(() -> callback.onPhotoDeleted());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}
