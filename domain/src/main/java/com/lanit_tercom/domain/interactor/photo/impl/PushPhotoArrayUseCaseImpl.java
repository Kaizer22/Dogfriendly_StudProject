package com.lanit_tercom.domain.interactor.photo.impl;

import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.photo.PushPhotoArrayUseCase;
import com.lanit_tercom.domain.interactor.photo.UseCase;
import com.lanit_tercom.domain.repository.PhotoRepository;

import java.util.ArrayList;

public class PushPhotoArrayUseCaseImpl extends UseCase implements PushPhotoArrayUseCase {
    private PushPhotoArrayUseCase.Callback callback;
    private String dirName;
    private ArrayList<String> uriStrings;

    public PushPhotoArrayUseCaseImpl(PhotoRepository photoRepository,
                               ThreadExecutor threadExecutor,
                               PostExecutionThread postExecutionThread) {
        super(photoRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(String dirName, ArrayList<String> uriStrings, Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.dirName = dirName;
        this.uriStrings = uriStrings;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.photoRepository.pushPhotoArray(dirName, uriStrings, this.repositoryCallback);
    }

    private final PhotoRepository.PushPhotoArrayCallback repositoryCallback =
            new PhotoRepository.PushPhotoArrayCallback() {
                @Override
                public void onPhotoArrayPushed(ArrayList<String> downloadUris) {
                    notifyPhotoArrayPushedSuccessfully(downloadUris);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyPhotoArrayPushedSuccessfully(ArrayList<String> downloadUris) {
        this.postExecutionThread.post(() -> callback.onPhotoArrayPushed(downloadUris));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }


}
