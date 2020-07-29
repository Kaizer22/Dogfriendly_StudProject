package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.content.Intent
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserSignInView
import com.lanit_tercom.dogfriendly_studproject.tests.ui.TestActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserSignInActivity
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignInFragment
import java.lang.Exception

/**
 * presenter класс для работы с авторизацией
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserSignInPresenter(private val authManager: AuthManager?) : BasePresenter() {

    var currentUserId: String? = null

    fun setView(view: UserSignInView) { this.view = view }

    fun auth(email: String?, password: String?) {

        authManager?.signOut(signOutCallback)

        authManager?.signInEmail(email, password, signInCallback)

    }

    private val signInCallback: AuthManager.SignInCallback = object : AuthManager.SignInCallback {
        //TODO удалить тестовый код!!!
        override fun OnSignInFinished(currentUserID: String?) {
            currentUserId = currentUserID
            ((view as UserSignInFragment)).hideLoading()
            if(currentUserId != null)
                //((view as UserSignInFragment).activity as UserSignInActivity).navigateToUserMap()
                ((view as UserSignInFragment).activity as UserSignInActivity).navigateToChat()
            else
                //не срабатывает... не знаю почему. Ведь такое же обращение к фрагменту работает сверху
                ((view as UserSignInFragment).showToastMessage("Неверный email или пароль"))
        }

        override fun OnError(e: Exception?) {}

    }

    private val signOutCallback: AuthManager.SignOutCallback = AuthManager.SignOutCallback { }


}