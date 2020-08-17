package com.lanit_tercom.dogfriendly_studproject.data.firebase.photo;

import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
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

    @Override
    public void getPhoto(String fileName, GetPhotoCallback getPhotoCallback) {
        storageReference.child(fileName).getDownloadUrl()
                .addOnSuccessListener(uri ->
                        getPhotoCallback.onPhotoLoaded(uri.toString()))
                .addOnFailureListener(e -> getPhotoCallback.onError(new RepositoryErrorBundle(e)));
        Log.i("TEST_ACTIVITY", "GET  "+ storageReference.child(fileName).getDownloadUrl().toString());
    }

    @Override
    public void pushPhoto(String fileName, String uriString, PushPhotoCallback pushPhotoCallback) {
        StorageReference fileReference = storageReference.child(fileName);
        fileReference.putFile(Uri.parse(uriString)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i("TEST_ACTIVITY", "PUSH  "+fileReference.getDownloadUrl().toString());
            }
        })
                .addOnFailureListener(e -> pushPhotoCallback.onError(new RepositoryErrorBundle(e)));

    }
}
