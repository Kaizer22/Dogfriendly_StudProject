package com.lanit_tercom.dogfriendly_studproject.mvp.presenter


import android.util.Log
import com.lanit_tercom.data.auth_manager.AuthManager
import com.lanit_tercom.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.BaseFragment
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignInFragment
import java.lang.Exception

/**
 * presenter класс для работы с конкретным пользователем
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserDetailPresenter(private val userDetailsView: UserDetailsView, private val authManager: AuthManager, private val useCaseTemp: UseCaseTemp) {

    fun renderUser(id: Int){
        val user = UseCaseTemp.users.find { it.id == id }
        userDetailsView.renderCurrentUser(user)
    }



}