package com.lanit_tercom.dogfriendly_studproject.data.firebase.photo;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import java.util.ArrayList;

public class FirebasePhotoStore implements PhotoStore {
    private static final String CHILD_PHOTOS = "Uploads";

    protected StorageReference storageReference;

    public FirebasePhotoStore() {
        storageReference = FirebaseStorage.getInstance().getReference().child(CHILD_PHOTOS);
    }


    @Override
    public void getPhoto(String fileName, GetPhotoCallback getPhotoCallback) {
        storageReference.child(fileName).getDownloadUrl()
                .addOnSuccessListener(uri -> getPhotoCallback.onPhotoLoaded(uri.toString()))
                .addOnFailureListener(e -> getPhotoCallback.onError(new RepositoryErrorBundle(e)));
    }


    @Override
    public void pushPhoto(String fileName, String uriString, PushPhotoCallback pushPhotoCallback) {
        StorageReference fileReference = storageReference.child(fileName);
        UploadTask uploadTask = fileReference.putFile(Uri.parse(uriString));

        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                pushPhotoCallback.onError(new RepositoryErrorBundle(task.getException()));
            }

            return fileReference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                pushPhotoCallback.onPhotoPushed(downloadUri.toString());
            } else {
                pushPhotoCallback.onError(new RepositoryErrorBundle(task.getException()));
            }
        });
    }


    @Override
    public void deletePhoto(String fileName, DeletePhotoCallback deletePhotoCallback) {
        storageReference.child(fileName).delete()
                .addOnSuccessListener(aVoid -> deletePhotoCallback.onPhotoDeleted())
                .addOnFailureListener(e -> deletePhotoCallback.onError(new RepositoryErrorBundle(e)));
    }


    private boolean syncFunction(boolean[] array) {
        for(boolean b: array) if(!b) return false;
        return true;
    }


    @Override
    public void pushPhotoArray(String dirName, ArrayList<String> uriStrings, PushPhotoArrayCallback pushPhotoArrayCallback) {


        ArrayList<String> downloadUris = new ArrayList<>();
        boolean[] booleanArray = new boolean[uriStrings.size()];

        for(int i = 0; i<uriStrings.size(); i++) {
            int position = i;
            String fileName = "photo" + position;

            StorageReference fileReference = storageReference.child(dirName).child(fileName);
            UploadTask uploadTask = fileReference.putFile(Uri.parse(uriStrings.get(i)));

            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    Log.i("TEST_ACTIVITY", "ERROR1");
                    pushPhotoArrayCallback.onError(new RepositoryErrorBundle(task.getException()));
                }


                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    booleanArray[position] = true;
                    downloadUris.add(downloadUri.toString());


                    if(syncFunction(booleanArray))
                        Log.i("TEST_ACTIVITY", "SUCCESS");
                        pushPhotoArrayCallback.onPhotoArrayPushed(downloadUris);

                } else {
                    Log.i("TEST_ACTIVITY", "ERROR2");
                    pushPhotoArrayCallback.onError(new RepositoryErrorBundle(task.getException()));
                }
            });

        }
    }

}

