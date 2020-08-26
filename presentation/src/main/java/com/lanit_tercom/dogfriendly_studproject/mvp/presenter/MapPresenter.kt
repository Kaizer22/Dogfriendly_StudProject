package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.graphics.Bitmap
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


    fun renderMap(userId: String?, avatar: String?, latitude: Double?, longitude: Double?){
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

    /**
     * Рассчитывает расстояние между двумя точками по широте и долготе
     * (если не нужна высота - подать на вход функции в параметрах el1, el2 0.0
     * @returns Дистанцию в метрах
     */
    fun distance(lat1: Double, lat2: Double, lon1: Double,
                 lon2: Double, el1: Double, el2: Double): Double {
        val R = 6371 // Radius of the earth
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        var distance = R * c * 1000 // convert to meters
        val height = el1 - el2
        distance = Math.pow(distance, 2.0) + Math.pow(height, 2.0)
        return Math.sqrt(distance)
    }

    /**
     * Изменение размера аватарок для карты (до 100х100 px)
     */
    fun resizeMapIcons(imageBitmap: Bitmap, multiplier: Int): Bitmap? {
        return Bitmap.createScaledBitmap(imageBitmap, 100, 100, false)
    }

}