package com.lanit_tercom.dogfriendly_studproject.ui.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Класс от которого наследуются все Activity.
 * Содержит функции для добавления и замены текущего фрагмента на экране
 * @author nikolaygorokhov1@gmail.com
 * @author prostak.sasha111@mail.ru
 */
abstract class BaseActivity : AppCompatActivity() {

    fun addFragment(containerViewId: Int, fragment: Fragment?) {
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.add(containerViewId, fragment!!)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun replaceFragment(containerViewId: Int, fragment: Fragment?) {
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerViewId, fragment!!)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}