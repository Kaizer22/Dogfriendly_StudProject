package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserSignInView
import java.lang.Exception

/**
 * presenter класс для работы с авторизацией
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserSignInPresenter(private val authManager: AuthManager?) : BasePresenter() {

    var currentUserId: String? = null

    fun setView(view: UserSignInView) {
        this.view = view
    }

    fun auth(email: String?, password: String?) {

        if(!authManager?.isSignedIn!!)
            authManager.signInEmail(email, password, signInCallback)

    }

    private val signInCallback: AuthManager.SignInCallback = object : AuthManager.SignInCallback {

        override fun OnSignInFinished(currentUserID: String?) {
            currentUserId = currentUserID
        }

        override fun OnError(e: Exception?) {
            TODO("Not yet implemented")
        }


    }


}