package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

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
        override fun onSignInFinished(currentUserID: String?) {
            currentUserId = currentUserID
            view?.hideLoading()
            if(currentUserId != null)
                //((view as UserSignInFragment).activity as UserSignInActivity).navigateToUserMap()
                //((view as UserSignInFragment).activity as UserSignInActivity).navigateToChat()
                ((view as SignInFragment).activity as SignInActivity).navigateToMainNavigation()
                //((view as SignInFragment).activity as SignInActivity).navigateToUserDetail(currentUserId)
                //((view as UserSignInFragment).activity as UserSignInActivity).navigateToChannelList(currentUserId!!)
            else
            //не срабатывает... не знаю почему. Ведь такое же обращение к фрагменту работает сверху
                ((view as SignInFragment).showToastMessage("Неверный email или пароль"))
        }

        override fun onError(e: Exception?) {}

    }

    private val signOutCallback: AuthManager.SignOutCallback = AuthManager.SignOutCallback { }

    override fun onDestroy() {
        this.view = null
    }


}