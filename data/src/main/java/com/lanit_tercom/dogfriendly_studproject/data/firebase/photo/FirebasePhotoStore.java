package com.lanit_tercom.dogfriendly_studproject.data.firebase.photo;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;

import java.util.ArrayList;

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
        UploadTask uploadTask = fileReference.putFile(Uri.parse(uriString));

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    pushPhotoCallback.onError(new RepositoryErrorBundle(task.getException()));
                }

                // Continue with the task to get the download URL
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    pushPhotoCallback.onPhotoPushed(downloadUri.toString());
                } else {
                    pushPhotoCallback.onError(new RepositoryErrorBundle(task.getException()));
                }
            }
        });


    }

    @Override
    public void deletePhoto(String fileName, DeletePhotoCallback deletePhotoCallback) {
        storageReference.child(fileName).delete()
                .addOnSuccessListener(aVoid -> deletePhotoCallback.onPhotoDeleted())
                .addOnFailureListener(e -> deletePhotoCallback.onError(new RepositoryErrorBundle(e)));
    }

    /**
     * Идея такая - пока список локальных uri не пуст мы добавляем uri в базу данных.
     * Как только он пуст - вызываем pushPhotoArrayCallback.onPhotoArrayPushed(downloadUris) с uri на сервер
     */
    @Override
    public void pushPhotoArray(String dirName, ArrayList<String> uriStrings, PushPhotoArrayCallback pushPhotoArrayCallback) {
        ArrayList<String> downloadUris = new ArrayList<>();

        while(!uriStrings.isEmpty()){
            String uriString = uriStrings.get(0);
            StorageReference dirReference = storageReference.child(dirName);
            UploadTask uploadTask = dirReference.putFile(Uri.parse(uriString));

            Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    pushPhotoArrayCallback.onError(new RepositoryErrorBundle(task.getException()));
                }

                return dirReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    downloadUris.add(downloadUri.toString());
                    uriStrings.remove(0);
                    if(uriStrings.isEmpty()){
                        pushPhotoArrayCallback.onPhotoArrayPushed(downloadUris);
                    }
                } else {
                    pushPhotoArrayCallback.onError(new RepositoryErrorBundle(task.getException()));
                }
            });

        }

    }


}

