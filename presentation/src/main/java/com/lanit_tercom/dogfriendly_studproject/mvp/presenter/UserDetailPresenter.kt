package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.user.DeletePetUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase

class UserDetailPresenter(private val getUserDetailsUseCase: GetUserDetailsUseCase?,
                          private val deletePetUseCase: DeletePetUseCase) : BasePresenter() {

    private var userId: String? = null

    fun initialize(userId: String?) {
        this.userId = userId
        Log.i("TEST_ACTIVITY", "LOAD USER DETAILS")
        this.loadUserDetails()
    }

    fun setView(view: UserDetailView){ this.view = view }

    //Функции вызывающие соответствующие callbacks
    private fun loadUserDetails() =
        getUserDetailsUseCase?.execute(userId, userDetailsCallback)

    fun deletePet(petId: String?) =
            deletePetUseCase.execute(userId, petId, deletePetCallback)


    private fun showUserDetailsInView(userDto: UserDto?) {
        val userDtoModelMapper = UserDtoModelMapper()
        val userModel = userDtoModelMapper.map2(userDto)
        Log.i("TEST_ACTIVITY","RENDER USER FROM PRESENTER")
        (view as UserDetailView).renderCurrentUser(userModel)

    }

    //Callbacks для загрузки данных пользователя и удаления питомца
    private val deletePetCallback: DeletePetUseCase.Callback = object : DeletePetUseCase.Callback{

        override fun onPetDeleted() {
            Log.i("TEST_ACTIVITY","PET DELETED")
        }

        override fun onError(errorBundle: ErrorBundle?) {}

    }

    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {

        override fun onUserDataLoaded(userDto: UserDto?) =
            this@UserDetailPresenter.showUserDetailsInView(userDto)

        override fun onError(errorBundle: ErrorBundle?) {}

    }


}