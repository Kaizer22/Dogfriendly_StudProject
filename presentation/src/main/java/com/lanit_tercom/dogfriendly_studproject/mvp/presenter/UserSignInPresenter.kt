package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.app.Activity
import com.lanit_tercom.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserSignInView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserMapFragment
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignInFragment
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignUpFragment
import java.lang.Exception

/**
 * presenter класс для работы с авторизацией
 * @author prostak.sasha111@mail.ru
 */

class UserSignInPresenter(private val userSignInView: UserSignInView, private val authManager: AuthManager, private val useCaseTemp: UseCaseTemp) {

    /*
        fun auth(email: String?, password: String?){
        try{
            authManager.signInEmail(email, password)
            userSignInView.toMapScreen()
        }catch (e: Exception){
            (userSignInView as UserSignInFragment).showToastMessage(e.message)
        }
    }
     */

    //Временный метод, пока не разберемся с data слоем (чуть выше - основной метод)

    fun auth(email: String?, password: String?){
        if (userSignInView is UserSignInFragment)
            UseCaseTemp.users.forEach {
                if(it.email == email && it.password == password)
                    userSignInView.toMapScreen()}
    }


}