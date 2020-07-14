package com.lanit_tercom.dogfriendly_studproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lanit_tercom.data.entity.UserEntity;
import com.lanit_tercom.data.firebase.FirebaseUserEntityStore;
import com.lanit_tercom.data.firebase.cache.UserCache;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Test getUserById() and getAllUsers() methods from FirebaseUserEntityStore
        FirebaseUserEntityStore firebaseUserEntityStore = new FirebaseUserEntityStore(new UserCache() {
            @Override
            public void saveUser(String userId, UserEntity userEntity) {

            }

            @Override
            public void getAllUsers(UserListCallback userListCallback) {

            }

            @Override
            public void getUserById(String id, UserByIdCallback userByIdCallback) {

            }
        });
    }
}
