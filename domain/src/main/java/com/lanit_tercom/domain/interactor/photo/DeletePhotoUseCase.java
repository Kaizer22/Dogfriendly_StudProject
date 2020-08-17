package com.lanit_tercom.domain.interactor.photo;

import com.lanit_tercom.domain.exception.ErrorBundle;

public interface DeletePhotoUseCase {
    interface Callback {
        void onPhotoDeleted();

        void onError(ErrorBundle errorBundle);
    }

    void execute(String filename, DeletePhotoUseCase.Callback callback);
}
