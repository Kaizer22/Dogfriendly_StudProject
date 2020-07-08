package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.google.android.gms.maps.GoogleMap
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment

/*
Пока не ясно что тут делать
 */
class UserDetailPresenter(userDetailsView: UserDetailsView) {
    private val userId: String? = null
    private var viewDetailsView: UserDetailsView = userDetailsView
    private val listOfActiveUsers: MutableList<UserModel> = mutableListOf()
    private val UseCaseTemp = UseCaseTemp()

    fun renderMap(){
        listOfActiveUsers.forEach {
            viewDetailsView.renderCurrentUser(it)
        }
    }

    fun fillListOfActiveUsers(){
        UseCaseTemp.fillList(listOfActiveUsers)
    }

}