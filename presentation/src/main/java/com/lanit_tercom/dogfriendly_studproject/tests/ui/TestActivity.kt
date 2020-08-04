package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.FirebaseUserEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStore
import com.lanit_tercom.domain.exception.ErrorBundle
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        button_get.setOnClickListener {
            FirebaseUserEntityStore(null).getUserById(editText_userId.text.toString(), object: UserEntityStore.UserByIdCallback{
                override fun onError(errorBundle: ErrorBundle?) {
                    textView.text = errorBundle?.errorMessage
                }

                override fun onUserLoaded(user: UserEntity?) {
                    textView.text = "${user?.id}\n${user?.userName}\n${user?.age}\n"
                }
            })
        }


    }
}