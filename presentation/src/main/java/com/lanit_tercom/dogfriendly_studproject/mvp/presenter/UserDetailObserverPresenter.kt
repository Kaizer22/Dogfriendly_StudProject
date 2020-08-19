package com.lanit_tercom.dogfriendly_studproject.mvp.presenter


import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase

/**
 * Просто грузит модель юзера из базы данных.
 */
class UserDetailObserverPresenter(private val getUserDetailsUseCase: GetUserDetailsUseCase?) : BasePresenter() {

    private var userId: String? = null

    fun initialize(userId: String?) {
        this.userId = userId
        this.loadUserDetails()
    }

    fun setView(view: UserDetailView){ this.view = view }

    private fun loadUserDetails() =
            getUserDetailsUseCase?.execute(userId, userDetailsCallback)

    private fun showUserDetailsInView(userDto: UserDto?) {
        val userDtoModelMapper = UserDtoModelMapper()
        val userModel = userDtoModelMapper.map2(userDto)
        (view as UserDetailView).renderCurrentUser(userModel)
    }

    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {

        override fun onUserDataLoaded(userDto: UserDto?) =
                showUserDetailsInView(userDto)

        override fun onError(errorBundle: ErrorBundle?) {}

    }

}