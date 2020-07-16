package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserMapView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.get.GetUserDetailsUseCase
import com.lanit_tercom.domain.interactor.get.GetUsersDetailsUseCase

/**
 * presenter класс для работы с картой
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserMapPresenter(private val getUsersDetailsUseCase: GetUsersDetailsUseCase) : BasePresenter(){

    fun setView(view: UserMapView){
        this.view = view
    }

    fun initialize() {
        this.loadUsersDetails()
    }

    private fun loadUsersDetails() {
        getUsersDetailsUseCase.execute(usersDetailsCallback)
    }

    fun renderMap(users: MutableList<UserDto>?){
        val usersModel = mutableListOf<UserModel?>()
        val userDtoModelMapper = UserDtoModelMapper()

        users?.forEach {
            val userModel = userDtoModelMapper.map2(it)
            usersModel.add(userModel)
        }

        usersModel.forEach {
            (view as UserMapView).renderUserOnMap(it)}
    }

    private val usersDetailsCallback = object: GetUsersDetailsUseCase.Callback{
        override fun onUsersDataLoaded(users: MutableList<UserDto>?) {
            this@UserMapPresenter.renderMap(users)
            Log.d("LOAD", "USERS");
        }

        override fun onError(errorBundle: ErrorBundle?) {
            TODO("Not yet implemented")
        }
    }
}