package com.lanit_tercom.dogfriendly_studproject.tests.ui;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.*;
import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.photo.PhotoStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.photo.PhotoStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.repository.PhotoRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.photo.GetPhotoUseCase;
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase;
import com.lanit_tercom.domain.interactor.photo.impl.GetPhotoUseCaseImpl;
import com.lanit_tercom.domain.interactor.photo.impl.PushPhotoUseCaseImpl;
import com.lanit_tercom.domain.repository.PhotoRepository;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;
import com.squareup.picasso.Picasso;


public class PhotoTestActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button getButton;
    private Button pushButton;
    private Button selectButton;
    private ImageView imageView;
    private Uri imageUri;
    private ThreadExecutor threadExecutor = JobExecutor.getInstance();
    private PostExecutionThread postExecutionThread = UIThread.getInstance();
    private NetworkManager networkManager = new NetworkManagerImpl(this);
    private PhotoStoreFactory photoStoreFactory = new PhotoStoreFactory(networkManager);
    private PhotoRepository photoRepository = new PhotoRepositoryImpl(photoStoreFactory);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_test_actiivty);


        imageView = findViewById(R.id.image_view);
        getButton = findViewById(R.id.button_get);
        pushButton = findViewById(R.id.button_push);
        selectButton = findViewById(R.id.button_select);

        getButton.setOnClickListener(v -> getPhoto());
        pushButton.setOnClickListener(v -> pushPhoto());
        selectButton.setOnClickListener(v -> selectPhoto());

    }

    private void selectPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);
        }
    }

    private void getPhoto() {
        GetPhotoUseCase getPhotoUseCase = new GetPhotoUseCaseImpl(photoRepository, threadExecutor, postExecutionThread);

        GetPhotoUseCase.Callback getPhotoCallback = new GetPhotoUseCase.Callback() {
            @Override
            public void onPhotoLoaded(String uriString) {
                //Пока почему то не срабатывает, но завтра должно сработать!
                Picasso.get()
                        .load(Uri.parse(uriString))
                        .placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(imageView);
                Log.i("TEST_ACTIVITY", "SUCCESS");
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                Log.i("TEST_ACTIVITY", "FAIL");
            }
        };
        getPhotoUseCase.execute("qwert", getPhotoCallback);

    }

    private void pushPhoto(){
        PushPhotoUseCase pushPhotoUseCase = new PushPhotoUseCaseImpl(photoRepository, threadExecutor, postExecutionThread);

        PushPhotoUseCase.Callback pushPhotoCallback = new PushPhotoUseCase.Callback() {

            @Override
            public void onPhotoPushed() {
                Log.i("TEST_ACTIVITY", "SUCCESS");
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                Log.i("TEST_ACTIVITY", "FAIL");
            }
        };

        pushPhotoUseCase.execute("qwert", imageUri.toString(), pushPhotoCallback);
    }

}
