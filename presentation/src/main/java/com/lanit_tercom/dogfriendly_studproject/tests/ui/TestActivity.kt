package com.lanit_tercom.dogfriendly_studproject.tests.ui


import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.lanit_tercom.dogfriendly_studproject.tests.ui.user_detail.EditTextActivity
import com.lanit_tercom.dogfriendly_studproject.tests.ui.user_detail.UserDetailTestActivity


/**
 * Запускается окно юзера
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        startActivity(Intent(this, UserDetailTestActivity::class.java))
        startActivity(Intent(this, UserDetailTestActivity::class.java))


    }

}





