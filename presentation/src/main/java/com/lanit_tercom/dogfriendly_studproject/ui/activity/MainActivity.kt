package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignInFragment


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
        val signInFragment = UserSignInFragment()

        if (savedInstanceState == null)
            addFragment(R.id.ft_container, signInFragment)
    }

}