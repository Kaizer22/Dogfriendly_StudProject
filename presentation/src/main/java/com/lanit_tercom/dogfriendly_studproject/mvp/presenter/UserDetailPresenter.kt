package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView

/**
 * presenter класс для работы с конкретным пользователем
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserDetailPresenter(private val authManager: AuthManager?, private val useCaseTemp: UseCaseTemp) : BasePresenter() {

    fun setView(view: UserDetailsView){
        this.view = view
    }

    fun renderUser(user: UserModel?) = (view as UserDetailsView).renderCurrentUser(user)

}