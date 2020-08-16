package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.SignInView
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailEditView
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase

/**
 * Презентер с функцией изменения юзера
 */
class UserDetailEditPresenter(private val editUserDetailsUseCase: EditUserDetailsUseCase?): BasePresenter() {

    private var view: UserDetailEditView? = null
    private var mapper = UserDtoModelMapper()

    fun setView(view: UserDetailEditView){ this.view = view }

    fun editUserDetails(user: UserModel?) =
            editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)

    private val editUserDetailsCallback: EditUserDetailsUseCase.Callback = object : EditUserDetailsUseCase.Callback {

        override fun onUserDataEdited() {}

        override fun onError(errorBundle: ErrorBundle?) {}

    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }
}