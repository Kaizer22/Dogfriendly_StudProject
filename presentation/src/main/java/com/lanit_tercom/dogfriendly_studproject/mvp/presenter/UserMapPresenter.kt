package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserMapView

/**
 * presenter класс для работы с картой
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserMapPresenter(private val useCaseTemp: UseCaseTemp) : BasePresenter(){

    fun setView(view: UserMapView){
        this.view = view
    }

    fun renderMap(users: MutableList<UserModel>?) =
            users?.forEach {
                (view as UserMapView).renderUserOnMap(it)}

}