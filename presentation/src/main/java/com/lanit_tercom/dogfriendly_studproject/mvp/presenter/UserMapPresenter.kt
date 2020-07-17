package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity
import com.lanit_tercom.dogfriendly_studproject.data.firebase.UserEntityStore
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserMapView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.get.GetUsersDetailsUseCase
import java.lang.Exception

/**
 * presenter класс для работы с картой
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserMapPresenter(private val useCaseTemp: UseCaseTemp) : BasePresenter(){

    fun setView(view: UserMapView){
        this.view = view
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
        }

        override fun onError(errorBundle: ErrorBundle?) {
            TODO("Not yet implemented")
        }
    }
}