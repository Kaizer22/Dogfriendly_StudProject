package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView

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