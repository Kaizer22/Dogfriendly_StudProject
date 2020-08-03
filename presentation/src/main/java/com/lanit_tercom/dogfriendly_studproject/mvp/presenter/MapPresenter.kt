package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.MapView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.user.GetUsersDetailsUseCase

/**
 * presenter класс для работы с картой
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class MapPresenter(private val getUsersDetailsUseCase: GetUsersDetailsUseCase) : BasePresenter(){

    private var view: MapView? = null

    fun setView(view: MapView){ this.view = view }

    fun initialize() = this.loadUsersDetails()


    private fun loadUsersDetails() =
        getUsersDetailsUseCase.execute(usersDetailsCallback)


    fun renderMap(users: MutableList<UserDto>?){
        val usersModel = mutableListOf<UserModel?>()
        val userDtoModelMapper = UserDtoModelMapper()

        users?.forEach {
            val userModel = userDtoModelMapper.map2(it)
            usersModel.add(userModel)
        }

        usersModel.forEach {
            view?.renderUserOnMap(it)}
    }

    private val usersDetailsCallback = object: GetUsersDetailsUseCase.Callback{

        override fun onUsersDataLoaded(users: MutableList<UserDto>?) =
            this@MapPresenter.renderMap(users)


        override fun onError(errorBundle: ErrorBundle?) {}

    }

    override fun onDestroy() {
        this.view = null
    }
}