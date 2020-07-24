package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignUpFragment

/**
 * Активность регистрации.
 * Запускает фрагмент с регистрацией.
 * @author prostak.sasha111@mail.ru
 */
class UserSignUpActivity : BaseActivity() {

    companion object{

        fun getCallingIntent(context: Context): Intent =
            Intent(context, UserSignUpActivity::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign_up)
    }

    fun navigateToUserSignIn() =
        navigator?.navigateToUserSignIn(this)


    override fun initializeActivity(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            addFragment(R.id.ft_container, UserSignUpFragment())
        }
    }

}