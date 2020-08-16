package com.lanit_tercom.dogfriendly_studproject.data.repository;


import com.lanit_tercom.dogfriendly_studproject.data.firebase.photo.PhotoStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.photo.PhotoStoreFactory;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.repository.PhotoRepository;

public class PhotoRepositoryImpl implements PhotoRepository {

    private static PhotoRepositoryImpl INSTANCE;

    public static synchronized PhotoRepositoryImpl getInstance(PhotoStoreFactory photoStoreFactory) {
        if (INSTANCE == null) {
            INSTANCE = new PhotoRepositoryImpl(photoStoreFactory);
        }
        return INSTANCE;
    }

    private final PhotoStoreFactory photoStoreFactory;


    public PhotoRepositoryImpl(PhotoStoreFactory photoStoreFactory) {

        if (photoStoreFactory == null) {
            throw new IllegalArgumentException("Invalid null parameters in constructor!!!");
        }

        this.photoStoreFactory = photoStoreFactory;
    }

    @Override
    public void getPhoto(String fileName, GetPhotoCallback getPhotoCallback) {
        PhotoStore photoStore = this.photoStoreFactory.create();
        photoStore.getPhoto(fileName, new PhotoStore.GetPhotoCallback() {
            @Override
            public void onPhotoLoaded(String uriString) {
                getPhotoCallback.onPhotoLoaded(uriString);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                getPhotoCallback.onError(errorBundle);
            }
        });
    }

    @Override
    public void pushPhoto(String fileName, String uriString, PushPhotoCallback pushPhotoCallback) {
        PhotoStore photoStore = this.photoStoreFactory.create();
        photoStore.pushPhoto(fileName, uriString, new PhotoStore.PushPhotoCallback() {
            @Override
            public void onPhotoPushed() {
                pushPhotoCallback.onPhotoPushed();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                pushPhotoCallback.onError(errorBundle);
            }
        });
    }


}
