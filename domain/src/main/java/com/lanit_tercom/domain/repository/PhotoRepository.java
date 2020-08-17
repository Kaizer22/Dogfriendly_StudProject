package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.exception.ErrorBundle;

public interface PhotoRepository {

    interface Error {
        void onError(ErrorBundle errorBundle);
    }

    interface GetPhotoCallback extends Error {
        void onPhotoLoaded(String uriString);
    }

    interface PushPhotoCallback extends Error {
        void onPhotoPushed(String downloadUri);
    }

    interface DeletePhotoCallback extends Error{
        void onPhotoDeleted();
    }

    void getPhoto(String fileName, GetPhotoCallback getPhotoCallback);
    void pushPhoto(String fileName, String uriString, PushPhotoCallback pushPhotoCallback);
    void deletePhoto(String fileName, DeletePhotoCallback deletePhotoCallback);

}