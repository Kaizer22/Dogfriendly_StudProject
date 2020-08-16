package com.lanit_tercom.domain.interactor.photo;

import com.lanit_tercom.domain.exception.ErrorBundle;

public interface PushPhotoUseCase {
    interface Callback {
        void onPhotoPushed();

        void onError(ErrorBundle errorBundle);
    }

    void execute(String filename, String uriString, PushPhotoUseCase.Callback callback);
}
