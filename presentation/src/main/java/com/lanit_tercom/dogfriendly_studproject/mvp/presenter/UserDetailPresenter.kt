package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.domain.interactor.get.GetUserDetailsUseCase

/**
 * presenter класс для работы с конкретным пользователем
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserDetailPresenter(private val authManager: AuthManager?, private val useCaseTemp: UseCaseTemp) : BasePresenter() {


    private val getUserDetailsUseCase: GetUserDetailsUseCase? = null
    private val userDtoModelMapper: UserDtoModelMapper? = null

    fun setView(view: UserDetailsView){
        this.view = view
    }

    fun renderUser(user: UserModel?) = (view as UserDetailsView).renderCurrentUser(user)


}