package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mvp.view.SignInView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.SignInActivity
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.SignInFragment
import java.lang.Exception

/**
 * presenter класс для работы с авторизацией
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class SignInPresenter(private val authManager: AuthManager?) : BasePresenter() {

    private var view: SignInView? = null
    var currentUserId: String? = null

    fun setView(view: SignInView) { this.view = view }

    fun auth(email: String?, password: String?) {

        authManager?.signOut(signOutCallback)

        authManager?.signInEmail(email, password, signInCallback)

    }

    private val signInCallback: AuthManager.SignInCallback = object : AuthManager.SignInCallback {
        //TODO удалить тестовый код!!!
        override fun OnSignInFinished(currentUserID: String?) {
            currentUserId = currentUserID
            view?.hideLoading()
            if(currentUserId != null)
                ((view as SignInFragment).activity as SignInActivity).navigateToUserMap()
            else
                //не срабатывает... не знаю почему. Ведь такое же обращение к фрагменту работает сверху
                ((view as SignInFragment).showToastMessage("Неверный email или пароль"))
        }

        override fun OnError(e: Exception?) {}

    }

    private val signOutCallback: AuthManager.SignOutCallback = AuthManager.SignOutCallback { }

    override fun onDestroy() {
        this.view = null
    }


}