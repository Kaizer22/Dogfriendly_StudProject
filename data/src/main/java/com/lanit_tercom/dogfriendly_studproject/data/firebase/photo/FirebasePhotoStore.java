package com.lanit_tercom.dogfriendly_studproject.data.firebase.photo;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;

public class FirebasePhotoStore implements PhotoStore{
    private static final String CHILD_PHOTOS = "Uploads";


    protected StorageReference storageReference;

    public FirebasePhotoStore() {
        storageReference = FirebaseStorage.getInstance().getReference().child(CHILD_PHOTOS);
    }

    /**
     * ВОТ ЭТОТ МЕТОД КАЖЕТСЯ НА САМОМ ДЕЛЕ СОВСЕМ НЕ НУЖЕН
     * МОЯ ЦЕЛЬ - СДЕЛАТЬ БЕЗ НЕГО
     */
    @Override
    public void getPhoto(String fileName, GetPhotoCallback getPhotoCallback) {
        storageReference.child(fileName).getDownloadUrl()
                .addOnSuccessListener(uri -> getPhotoCallback.onPhotoLoaded(uri.toString()))
                .addOnFailureListener(e -> getPhotoCallback.onError(new RepositoryErrorBundle(e)));

    }

    /**
     * ЭТОТ МЕТОД ДОЛЖЕН НЕ ТОЛЬКО ДОБАВИТЬ ФОТО В БАЗУ, НО И
     * ПРОТАЩИТЬ НА ВЕРХНИЕ СЛОИ ССЫЛКУ НА ДОБАВЛЕННОЕ ФОТО
     */
    @Override
    public void pushPhoto(String fileName, String uriString, PushPhotoCallback pushPhotoCallback) {
        StorageReference fileReference = storageReference.child(fileName);
        fileReference.putFile(Uri.parse(uriString))
                .addOnSuccessListener(taskSnapshot -> pushPhotoCallback.onPhotoPushed(fileReference.getDownloadUrl().toString()))
                .addOnFailureListener(e -> pushPhotoCallback.onError(new RepositoryErrorBundle(e)));


    }

    @Override
    public void deletePhoto(String fileName, DeletePhotoCallback deletePhotoCallback) {
        storageReference.child(fileName).delete()
                .addOnSuccessListener(aVoid -> deletePhotoCallback.onPhotoDeleted())
                .addOnFailureListener(e -> deletePhotoCallback.onError(new RepositoryErrorBundle(e)));
    }


}
