package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserMapView

/**
 * presenter класс для работы с картой
 * @author prostak.sasha111@mail.ru
 */

class UserMapPresenter(private val userMapView: UserMapView, private val authManager: AuthManager, private val useCaseTemp: UseCaseTemp) {

    fun renderMap() =
            UseCaseTemp.users.forEach {
                userMapView.renderUserOnMap(it)}

}