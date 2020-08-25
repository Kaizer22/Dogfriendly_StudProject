package com.lanit_tercom.domain.interactor.photo.impl;

import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.photo.DeletePhotoArrayUseCase;
import com.lanit_tercom.domain.interactor.photo.UseCase;
import com.lanit_tercom.domain.repository.PhotoRepository;

import java.util.ArrayList;

public class DeletePhotoArrayUseCaseImpl extends UseCase implements DeletePhotoArrayUseCase {
    private DeletePhotoArrayUseCase.Callback callback;
    private ArrayList<String> photoList;


    public DeletePhotoArrayUseCaseImpl(PhotoRepository photoRepository,
                                  ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        super(photoRepository, threadExecutor, postExecutionThread);
    }

    public void execute(ArrayList<String> photoList, DeletePhotoArrayUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.photoList = photoList;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.photoRepository.deletePhotoArray(photoList, repositoryCallback);
    }

    private final PhotoRepository.DeletePhotoArrayCallback repositoryCallback =
            new PhotoRepository.DeletePhotoArrayCallback() {
                @Override
                public void onPhotoArrayDeleted() {
                    notifyPhotoArrayDeletedSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };



    private void notifyPhotoArrayDeletedSuccessfully() {
        this.postExecutionThread.post(() -> callback.onPhotoDeleted());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }




}
