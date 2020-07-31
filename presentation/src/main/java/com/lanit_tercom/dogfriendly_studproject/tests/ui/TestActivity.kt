package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.lanit_tercom.dogfriendly_studproject.R


/**
 * Закоменченный код - нерабочая версия с CollapsingToolBar
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail_test)

        val appbar = findViewById<View>(R.id.appbar) as AppBarLayout
        val heightDp = resources.displayMetrics.heightPixels  * 0.5 - (48+10) * resources.displayMetrics.density
        val lp = appbar.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = heightDp.toInt()
    }

}

