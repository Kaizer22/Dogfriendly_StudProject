package com.lanit_tercom.domain.interactor.photo;


import com.lanit_tercom.domain.exception.ErrorBundle;

public interface GetPhotoUseCase{
    interface Callback {
        void onPhotoLoaded(String uriString);

        void onError(ErrorBundle errorBundle);
    }

    void execute(String filename, GetPhotoUseCase.Callback callback);
}
