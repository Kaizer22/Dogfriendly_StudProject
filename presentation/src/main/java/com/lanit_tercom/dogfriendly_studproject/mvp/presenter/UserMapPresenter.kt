package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserMapView

/**
 * presenter класс для работы с картой
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserMapPresenter(private val authManager: AuthManager?, private val useCaseTemp: UseCaseTemp) : BasePresenter(){

    fun setView(view: UserMapView){
        this.view = view
    }

    fun renderMap() =
            UseCaseTemp.users.forEach {
                (view as UserMapView).renderUserOnMap(it)}

}