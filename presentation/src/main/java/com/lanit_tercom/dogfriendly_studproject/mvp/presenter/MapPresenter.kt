package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.data.geofire.UserGeoFire
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
    private var users: MutableList<UserDto>? = null

    fun setView(view: MapView){ this.view = view }

    fun initialize(userId: String, radius: Double){
        getUsersDetails(usersDetailsCallback)
        this.loadUsersCoordinates(userId, radius)
    }



    private fun loadUsersCoordinates(userId: String, radius: Double){
        UserGeoFire().userQueryAtLocation(userId, radius, this.userQueryAtLocationCallback)
    }

    fun getUsersDetails(getUsersDetailsUseCaseCallback: GetUsersDetailsUseCase.Callback){
        getUsersDetailsUseCase.execute(getUsersDetailsUseCaseCallback)
    }


    fun renderMap(userId: String?, avatar: String, latitude: Double?, longitude: Double?){
        view?.renderUserOnMap(userId, avatar, latitude, longitude)
    }

    private val usersDetailsCallback = object: GetUsersDetailsUseCase.Callback{

        override fun onUsersDataLoaded(users: MutableList<UserDto>?){
            this@MapPresenter.users = users
        }


        override fun onError(errorBundle: ErrorBundle?) {}

    }

    private val userQueryAtLocationCallback = object: UserGeoFire.UserQueryAtLocationCallback{
        override fun onError(exception: Exception?) {
        }

        override fun onQueryLoaded(key: String?, latitude: Double?, longitude: Double?) {
            val user =users?.find { it.id == key }
            if (user != null && user.pets != null){
                for (pet in user.pets){
                    this@MapPresenter.renderMap(pet.key, pet.value.avatar , latitude, longitude)
                    return
                }
            }
        }
    }

    override fun onDestroy() {
        this.view = null
    }


}