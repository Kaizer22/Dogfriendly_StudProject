package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.google.android.gms.maps.GoogleMap
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment

/**
 * presenter класс для работы с пользователями
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserDetailPresenter(userDetailsView: UserDetailsView) {
    private val userId: String? = null
    private var viewDetailsView: UserDetailsView = userDetailsView
    val listOfActiveUsers: MutableList<UserModel> = mutableListOf()
    private val UseCaseTemp = UseCaseTemp()

    fun renderMap(){
        listOfActiveUsers.forEach {
            viewDetailsView.renderCurrentUser(it)
        }
    }
    fun fillListOfActiveUsers(){
        UseCaseTemp.fillList(listOfActiveUsers)
    }

    fun renderUser(id: Int?){
        val user = listOfActiveUsers.find { it.id == id }
        viewDetailsView.renderCurrentUser(user)
    }

    fun auth(){

    }



}