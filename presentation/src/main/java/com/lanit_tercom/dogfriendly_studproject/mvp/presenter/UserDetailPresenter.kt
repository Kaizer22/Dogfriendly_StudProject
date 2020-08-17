package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.net.Uri
import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.GetPhotoUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase
import com.lanit_tercom.domain.interactor.user.DeletePetUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase

/**
 * Презентер с функцией загрузки юзера и удаления питомца
 */
class UserDetailPresenter(private val getUserDetailsUseCase: GetUserDetailsUseCase?,
                          private val deletePetUseCase: DeletePetUseCase,
                          private val getPhotoUseCase: GetPhotoUseCase) : BasePresenter() {

    private var userId: String? = null

    fun initialize(userId: String?) {
        this.userId = userId
        this.loadUserDetails()
    }

    fun setView(view: UserDetailView){ this.view = view }

    //Функции вызывающие соответствующие callbacks
    private fun loadUserDetails() =
        getUserDetailsUseCase?.execute(userId, userDetailsCallback)

    fun deletePet(petId: String?) =
            deletePetUseCase.execute(userId, petId, deletePetCallback)

    //Вызывается при получении данных пользователя с UserDetailsCallback.
    //Внутри вызывается ЕЩЕ ОДИН callback (GetPhotoCallback) для получения аватара юзера
    //А этот callback уже после получения всех данных рендерит модельку на экран.
    private fun showUserDetailsInView(userDto: UserDto?) {
        val userDtoModelMapper = UserDtoModelMapper()
        val userModel = userDtoModelMapper.map2(userDto)

        var getPhotoCallback: GetPhotoUseCase.Callback = object : GetPhotoUseCase.Callback {
            override fun onPhotoLoaded(uriString: String?) {
                userModel.avatar = Uri.parse(uriString)
                (view as UserDetailView).renderCurrentUser(userModel)
            }

            override fun onError(errorBundle: ErrorBundle) {}
        }

        getPhotoUseCase.execute("$userId/avatar", getPhotoCallback)


    }

    //Callbacks для загрузки данных пользователя и удаления питомца
    private val deletePetCallback: DeletePetUseCase.Callback = object : DeletePetUseCase.Callback{

        override fun onPetDeleted() {}

        override fun onError(errorBundle: ErrorBundle?) {}

    }

    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {

        override fun onUserDataLoaded(userDto: UserDto?) =
            this@UserDetailPresenter.showUserDetailsInView(userDto)

        override fun onError(errorBundle: ErrorBundle?) {}

    }


}