package com.lanit_tercom.dogfriendly_studproject.data.firebase.photo;

import android.net.Uri;

import com.lanit_tercom.domain.exception.ErrorBundle;

public interface PhotoStore {

    interface Error {
        void onError(ErrorBundle errorBundle);
    }

    interface GetPhotoCallback extends Error{
        void onPhotoLoaded(String uriString);
    };

    interface PushPhotoCallback extends Error{
        void onPhotoPushed();
    };

    void getPhoto(String fileName, GetPhotoCallback getPhotoCallback);
    void pushPhoto(String fileName, String uriString, PushPhotoCallback pushPhotoCallback);

}
