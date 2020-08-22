package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.user.DeletePetUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase

class UserDetailPresenter(private val getUserDetailsUseCase: GetUserDetailsUseCase?,
                          private val deletePetUseCase: DeletePetUseCase,
                          private val deletePhotoUseCase: DeletePhotoUseCase) : BasePresenter() {

    private var userId: String? = null

    fun initialize(userId: String?) {
        this.userId = userId
        this.loadUserDetails()
    }

    fun setView(view: UserDetailView) {
        this.view = view
    }

    //Функции вызывающие соответствующие callbacks
    private fun loadUserDetails() =
            getUserDetailsUseCase?.execute(userId, userDetailsCallback)

    fun deletePet(petId: String?) {

        val deletePetCallback: DeletePetUseCase.Callback = object : DeletePetUseCase.Callback {

            override fun onPetDeleted() {}

            override fun onError(errorBundle: ErrorBundle?) {
            }

        }

        deletePetUseCase.execute(userId, petId, deletePetCallback)

        val deletePhotoCallback: DeletePhotoUseCase.Callback = object : DeletePhotoUseCase.Callback {

            override fun onPhotoDeleted() {}

            override fun onError(errorBundle: ErrorBundle?) {}

        }

        deletePhotoUseCase.execute("$userId/$petId/pet_photos", deletePhotoCallback)
        deletePhotoUseCase.execute("$userId/$petId/avatar", deletePhotoCallback)

    }


    private fun showUserDetailsInView(userDto: UserDto?) {
        val userDtoModelMapper = UserDtoModelMapper()
        val userModel = userDtoModelMapper.map2(userDto)
        (view as UserDetailView).renderCurrentUser(userModel)


    }

    //Callbacks для загрузки данных пользователя и удаления питомца

    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {

        override fun onUserDataLoaded(userDto: UserDto?) =
                this@UserDetailPresenter.showUserDetailsInView(userDto)

        override fun onError(errorBundle: ErrorBundle?) {}

    }


}