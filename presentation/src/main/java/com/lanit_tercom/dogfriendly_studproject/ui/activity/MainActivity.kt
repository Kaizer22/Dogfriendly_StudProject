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
        // Проверка на то, создается ли актвиность впервые
        if (savedInstanceState == null)
            addFragment(R.id.activity_main, mapFragment)
    }

    //Позволяет использовать protected функцию из BaseActivity в других классах.
    //Скорее всего это не очень хороший подход
    fun replace(fragment: Fragment)
        = replaceFragment(R.id.activity_main, fragment)




}