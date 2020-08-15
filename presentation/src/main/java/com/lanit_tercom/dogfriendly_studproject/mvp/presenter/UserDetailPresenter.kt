package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase

/**
 * presenter класс для работы с конкретным пользователем
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserDetailPresenter(private val getUserDetailsUseCase: GetUserDetailsUseCase?) : BasePresenter() {

    private var view: UserDetailsView? = null
    private var userId: String? = null

    fun initialize(userId: String?) {
        this.userId = userId
        this.loadUserDetails()
    }

    fun setView(view: UserDetailsView){ this.view = view }

    private fun loadUserDetails() =
        getUserDetailsUseCase!!.execute(userId, userDetailsCallback)


    private fun showUserDetailsInView(userDto: UserDto?) {
        val userDtoModelMapper = UserDtoModelMapper()
        val userModel = userDtoModelMapper.map2(userDto)
        view?.renderCurrentUser(userModel)
    }

    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {

        override fun onUserDataLoaded(userDto: UserDto?) =
            this@UserDetailPresenter.showUserDetailsInView(userDto)

        override fun onError(errorBundle: ErrorBundle?) {}

    }

    override fun onDestroy() {
        this.view = null
    }
}