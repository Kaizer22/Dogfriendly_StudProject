package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.OnBackButtonListener
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignInFragment

/**
 * Главная активность.
 * Запускает фрагмент с картой.
 * @author nikolaygorokhov1@gmail.com
 * @author prostak.sasha111@mail.ru
 *
 */
class MainActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signInFragment = UserSignInFragment()

        if (savedInstanceState == null)
            addFragment(R.id.ft_container, signInFragment)
    }

    override fun onBackPressed() {
        val backStackCount = supportFragmentManager.backStackEntryCount

        if (backStackCount > 0) {
            val currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.ft_container)
            if (currentFragment is OnBackButtonListener){
                if (currentFragment.onBackPressed()) return
            }
        }

        super.onBackPressed()
    }
}