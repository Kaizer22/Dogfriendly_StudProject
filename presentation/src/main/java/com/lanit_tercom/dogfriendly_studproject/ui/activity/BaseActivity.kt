package com.lanit_tercom.dogfriendly_studproject.ui.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {
    /**
     * Adds a [Fragment] to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected fun addFragment(containerViewId: Int, fragment: Fragment?) {
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.add(containerViewId, fragment!!)
        fragmentTransaction.commit()
    }
}