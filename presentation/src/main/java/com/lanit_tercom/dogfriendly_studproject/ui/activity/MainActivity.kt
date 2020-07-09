package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserMapFragment


/**
 * Главная активность.
 * Запускает фрагмент с картой.
 * @author nikolaygorokhov1@gmail.com
 * @author prostak.sasha111@mail.ru
 */
class MainActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = UserMapFragment()

        if (savedInstanceState == null)
            addFragment(R.id.activity_main, mapFragment)
    }




}