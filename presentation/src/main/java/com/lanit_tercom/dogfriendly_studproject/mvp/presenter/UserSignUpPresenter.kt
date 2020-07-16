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

    fun setView(view: UserSignUpView) {
        this.view = view
        Log.i("AUTH_MANAGER", authManager?.isSignedIn.toString())
    }

    fun registerUser(email: String?, password: String?) {
        authManager?.createUserWithEmailPassword(email, password, createUserCallback)
    }

    private val createUserCallback: AuthManager.CreateUserCallback = object : AuthManager.CreateUserCallback {

        override fun OnCreateUserFinished(currentUserID: String?) {
            Log.i("AUTH_MANAGER", "A new user has been created.")
            currentUserId = currentUserID
        }

        override fun OnError(e: Exception?) {
            TODO("Not yet implemented")
        }


    }

}