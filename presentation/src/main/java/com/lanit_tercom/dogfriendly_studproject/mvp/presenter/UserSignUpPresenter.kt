package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserSignUpView
import com.lanit_tercom.dogfriendly_studproject.navigation.Navigator
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserSignUpActivity
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignInFragment
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignUpFragment

/**
 * presenter класс для работы с регистрацией
 * @author prostak.sasha111@mail.ru
 */
class UserSignUpPresenter(private val authManager: AuthManager?, private val useCaseTemp: UseCaseTemp) : BasePresenter() {

    fun setView(view: UserSignUpView) {
        this.view = view
    }

//    fun registerUser(email: String?, password: String?, name: String?) {
//        authManager?.createUserWithEmailPassword(email, password)
//        var maxId = UseCaseTemp.users.maxBy { it.id }!!.id
//        useCaseTemp.addUser(UserModel(++maxId, name!!, email!!, password!!, Point(21.8, 42.3)))
//        Navigator().navigateToUserMap((view as UserSignUpFragment).requireContext())
//    }


    fun registerUser(email: String?, password: String?, name: String?) {
        var maxId = loadUsers().maxBy { it.id }!!.id
        useCaseTemp.addUser(UserModel(++maxId, name!!, email!!, password!!, Point(21.8, 42.3)))
        ((view as UserSignUpFragment).activity as UserSignUpActivity).navigateToUserMap()
    }

}