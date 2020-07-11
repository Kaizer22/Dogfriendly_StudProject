package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserMapFragment

/**
 * Активность карты.
 * Запускает фрагмент с картой.
 * @author prostak.sasha111@mail.ru
 *
 */

class UserMapActivity : BaseActivity() {

    companion object{

        fun getCallingIntent(context: Context): Intent {
            return Intent(context, UserMapActivity::class.java)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_map)
        initialize()
        initializeActivity(savedInstanceState)
    }

    fun navigateToUserDetail(userId: Int?){
        navigator?.navigateToUserDetail(this, userId)
    }

    private fun initializeActivity(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            addFragment(R.id.ft_container, UserMapFragment())
        }
    }

    override fun onBackPressed() {
        return
    }

}