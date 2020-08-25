package com.lanit_tercom.domain.interactor.photo;

import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.ArrayList;

public interface DeletePhotoArrayUseCase {
    interface Callback {
        void onPhotoDeleted();

        void onError(ErrorBundle errorBundle);
    }

    void execute(ArrayList<String> photoList, DeletePhotoArrayUseCase.Callback callback);
}
