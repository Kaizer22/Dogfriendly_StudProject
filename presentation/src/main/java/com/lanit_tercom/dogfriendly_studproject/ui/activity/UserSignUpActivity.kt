package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignUpFragment

/**
 * Активность регистрации.
 * Запускает фрагмент с регистрацией.
 * @author prostak.sasha111@mail.ru
 *
 */

class UserSignUpActivity : BaseActivity() {

    companion object{

        fun getCallingIntent(context: Context): Intent {
            return Intent(context, UserSignUpActivity::class.java)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign_up)
        initialize()
        initializeActivity(savedInstanceState)
    }

    fun navigateToUserMap(){
        navigator?.navigateToUserMap(this)
    }

    private fun initializeActivity(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            addFragment(R.id.ft_container, UserSignUpFragment())
        }
    }
}