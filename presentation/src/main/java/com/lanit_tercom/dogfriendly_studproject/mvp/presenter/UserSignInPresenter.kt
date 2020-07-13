package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.data.auth_manager.AuthManager
import com.lanit_tercom.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserSignInView
import com.lanit_tercom.dogfriendly_studproject.navigation.Navigator
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserSignInActivity
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignInFragment

/**
 * presenter класс для работы с авторизацией
 * @author prostak.sasha111@mail.ru
 */
class UserSignInPresenter(private val authManager: AuthManager?, private val useCaseTemp: UseCaseTemp) : BasePresenter() {

    fun setView(view: UserSignInView) {
        this.view = view
    }


//    fun auth(email: String?, password: String?) {
//        try {
//            authManager?.signInEmail(email, password)
//            Navigator().navigateToUserMap((view as UserSignInFragment).requireContext())
//        } catch (e: Exception) {
//            (view as UserSignInFragment).showToastMessage(e.message)
//        }
//    }


    fun auth(email: String?, password: String?){
        if (view is UserSignInFragment)
            loadUsers().forEach {
                if(it.email == email && it.password == password)
                    ((view as UserSignInFragment).activity as UserSignInActivity).navigateToUserMap()}
    }

}