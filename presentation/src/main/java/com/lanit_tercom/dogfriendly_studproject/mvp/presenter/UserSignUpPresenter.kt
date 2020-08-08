package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserSignUpView
import java.lang.Exception

/**
 * presenter класс для работы с регистрацией
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserSignUpPresenter(private val authManager: AuthManager?) : BasePresenter() {

    var currentUserId: String? = null

    fun setView(view: UserSignUpView) { this.view = view }

    fun registerUser(email: String?, password: String?) =
        authManager?.createUserWithEmailPassword(email, password, createUserCallback)


    private val createUserCallback: AuthManager.CreateUserCallback = object : AuthManager.CreateUserCallback {

        override fun onCreateUserFinished(currentUserID: String?) {
            currentUserId = currentUserID
        }

        override fun onError(e: Exception?) {
            //TODO("Not yet implemented")
        }


    }

}