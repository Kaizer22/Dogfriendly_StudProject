package com.lanit_tercom.dogfriendly_studproject.tests.ui


import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.lanit_tercom.dogfriendly_studproject.tests.ui.map.MapSettingsActivity
import com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail.PetDetailTestActivity
import com.lanit_tercom.dogfriendly_studproject.tests.ui.user_detail.UserDetailTestActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.MapActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.SignInActivity


/**
 * Запускается окно юзера
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        startActivity(Intent(this, UserDetailTestActivity::class.java))
        startActivity(Intent(this, MapActivity::class.java))


    }

}





