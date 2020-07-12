package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lanit_tercom.dogfriendly_studproject.navigation.Navigator

/**
 * Класс от которого наследуются все Activity.
 * Содержит функции для добавления и замены текущего фрагмента на экране
 * @author nikolaygorokhov1@gmail.com
 * @author prostak.sasha111@mail.ru
 */
abstract class BaseActivity : AppCompatActivity() {

    protected var navigator: Navigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.navigator = Navigator()
        initializeActivity(savedInstanceState)
    }

    fun addFragment(containerViewId: Int, fragment: Fragment?) {
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        if (fragment != null)
            fragmentTransaction.add(containerViewId, fragment)
        fragmentTransaction.commit()
    }

    fun replaceFragment(containerViewId: Int, fragment: Fragment?) {
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        if (fragment != null)
            fragmentTransaction.replace(containerViewId, fragment)
        fragmentTransaction.commit()
    }

    protected abstract fun initializeActivity(savedInstanceState: Bundle?)

}