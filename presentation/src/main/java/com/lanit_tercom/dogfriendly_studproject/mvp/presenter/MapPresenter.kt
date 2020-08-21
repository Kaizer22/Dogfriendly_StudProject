package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.data.geofire.UserGeoFire
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.MapView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.user.GetUsersDetailsUseCase
import java.lang.Exception

/**
 * presenter класс для работы с картой
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class MapPresenter(private val getUsersDetailsUseCase: GetUsersDetailsUseCase) : BasePresenter(){

    private var view: MapView? = null

    fun setView(view: MapView){ this.view = view }

    fun initialize(userId: String, radius: Double) = this.loadUsersCoordinates(userId, radius)



    private fun loadUsersCoordinates(userId: String, radius: Double){
        UserGeoFire().userQueryAtLocation(userId, radius, this.userQueryAtLocationCallback)
    }

    fun getUsersDetails(getUsersDetailsUseCaseCallback: GetUsersDetailsUseCase.Callback){
        getUsersDetailsUseCase.execute(getUsersDetailsUseCaseCallback)
    }


    fun renderMap(userId: String?, latitude: Double?, longitude: Double?){
        view?.renderUserOnMap(userId, latitude, longitude)
    }

//    private val usersDetailsCallback = object: GetUsersDetailsUseCase.Callback{
//
//        override fun onUsersDataLoaded(users: MutableList<UserDto>?) =
//            this@MapPresenter.renderMap(users)
//
//
//        override fun onError(errorBundle: ErrorBundle?) {}
//
//    }
    private val userQueryAtLocationCallback = object: UserGeoFire.UserQueryAtLocationCallback{
        override fun onError(exception: Exception?) {
        }

        override fun onQueryLoaded(key: String?, latitude: Double?, longitude: Double?) {
            this@MapPresenter.renderMap(key, latitude, longitude)
        }
    }

    override fun onDestroy() {
        this.view = null
    }
}