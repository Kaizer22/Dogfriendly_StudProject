package com.lanit_tercom.domain.interactor.photo;

import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.ArrayList;

public interface PushPhotoArrayUseCase {

    interface Callback {
        void onPhotoArrayPushed(ArrayList<String> downloadUris);

        void onError(ErrorBundle errorBundle);
    }

    void execute(String dirName, ArrayList<String> uriStrings, PushPhotoArrayUseCase.Callback callback);
}
