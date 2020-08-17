package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailEditView
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase

/**
 * Презентер с функцией изменения юзера
 */
class UserDetailEditPresenter(private val editUserDetailsUseCase: EditUserDetailsUseCase?, private val pushPhotoUseCase: PushPhotoUseCase?): BasePresenter() {

    private var mapper = UserDtoModelMapper()

    fun setView(view: UserDetailEditView){ this.view = view }

    //Функции вызывающие соответствующие callbacks
    fun editUserDetails(user: UserModel?) =
            editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)

    fun pushPhoto(filename: String, uriString: String) =
            pushPhotoUseCase?.execute(filename, uriString, pushPhotoCallback)

    //Callbacks для редактирования юзера и отправки фото в базу данных
    private val editUserDetailsCallback: EditUserDetailsUseCase.Callback = object : EditUserDetailsUseCase.Callback {

        override fun onUserDataEdited() {}

        override fun onError(errorBundle: ErrorBundle?) {}

    }

    private var pushPhotoCallback: PushPhotoUseCase.Callback = object : PushPhotoUseCase.Callback {
        override fun onPhotoPushed() {}

        override fun onError(errorBundle: ErrorBundle) {}
    }

}