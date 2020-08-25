package com.lanit_tercom.dogfriendly_studproject.tests.ui


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserDetailActivity


/**
 * Запускается окно юзера
 */
class TestActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, UserDetailActivity::class.java))


    }

}





