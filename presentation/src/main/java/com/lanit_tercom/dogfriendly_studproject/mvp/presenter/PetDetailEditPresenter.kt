package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.net.Uri
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailEditView
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.PetDetailEditFragment
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase
import com.lanit_tercom.domain.interactor.user.DeletePetUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase


class PetDetailEditPresenter(private val userId: String?,
                             private val pet: PetModel,
                             private val getUserDetailsUseCase: GetUserDetailsUseCase): BasePresenter(){

    fun setView(view: PetDetailEditView){ this.view = view }

    //Запрашивает у бд модельку юзера
    fun loadPetDetails() =
            getUserDetailsUseCase.execute(userId, userDetailsCallback)

    //Ищет есть ли в массиве питомцев питомец с определенным id
    private fun findPet(id: String?, pets: ArrayList<PetModel>): PetModel?{
        for(pet in pets)
            if(pet.id == id) return pet
        return null
    }

    //Если питомец с таким id уже есть - грузим в поля ввода и аватар готовые значения с firebase
    private fun showPetDetailsInView(userDto: UserDto?) {
        val userDtoModelMapper = UserDtoModelMapper()
        val userModel = userDtoModelMapper.map2(userDto)
        if(userModel.pets!=null){
            val model = findPet(pet.id, userModel.pets as ArrayList)
            (view as PetDetailEditFragment).initializeView(model)
        }


    }

    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {

        override fun onUserDataLoaded(userDto: UserDto?) = showPetDetailsInView(userDto)

        override fun onError(errorBundle: ErrorBundle?) {}

    }
}
