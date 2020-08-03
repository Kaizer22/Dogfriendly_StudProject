package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lanit_tercom.dogfriendly_studproject.R


/**
 * Закоменченный код - нерабочая версия с CollapsingToolBar
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val toPetCharacterEdit = Intent(this, PetCharacterEditTestActivity::class.java)
//        startActivity(toPetCharacterEdit)

//          val toPetPhotoEdit = Intent(this, PetPhotoEditTestActivity::class.java)
//          startActivity(toPetPhotoEdit)

          val toPetDetail = Intent(this, PetDetailTestActivity::class.java)
          startActivity(toPetDetail)

    }

}



