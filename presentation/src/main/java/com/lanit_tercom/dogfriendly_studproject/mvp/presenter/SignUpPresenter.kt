package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mvp.view.SignUpView
import java.lang.Exception

/**
 * presenter класс для работы с регистрацией
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class SignUpPresenter(private val authManager: AuthManager?) : BasePresenter() {

    private var view: SignUpView? = null
    var currentUserId: String? = null

    fun setView(view: SignUpView) { this.view = view }

    fun registerUser(email: String?, password: String?) =
        authManager?.createUserWithEmailPassword(email, password, createUserCallback)


    private val createUserCallback: AuthManager.CreateUserCallback = object : AuthManager.CreateUserCallback {

        override fun OnCreateUserFinished(currentUserID: String?) {
            currentUserId = currentUserID
        }

        override fun OnError(e: Exception?) {
            //TODO("Not yet implemented")
        }


    }

    override fun onDestroy() {
        this.view = null
    }

}