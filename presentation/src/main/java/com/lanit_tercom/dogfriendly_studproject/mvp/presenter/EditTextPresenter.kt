package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.EditTextView
import com.lanit_tercom.dogfriendly_studproject.mvp.view.LoadDataView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase

class EditTextPresenter(private val getUserDetailsUseCase: GetUserDetailsUseCase?, private val editUserDetailsUseCase: EditUserDetailsUseCase?): BasePresenter() {

    private var userId: String? = null
    var user: UserModel? = null
    private var mapper = UserDtoModelMapper()
    private var view: LoadDataView? = null

    //Загрузка модели юзера, тексты которой будем редачит
    fun initialize(userId: String?) {
        this.userId = userId
        loadUserDetails()
    }

    private fun loadUserDetails() =
            getUserDetailsUseCase?.execute(userId, userDetailsCallback)

    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {

        override fun onUserDataLoaded(userDto: UserDto?){
            user = mapper.map2(userDto)
        }


        override fun onError(errorBundle: ErrorBundle?) {}

    }

    fun setView(view: EditTextView){ this.view = view }

    fun editTextFields() {
        val editUserDetailsCallback: EditUserDetailsUseCase.Callback = object : EditUserDetailsUseCase.Callback {

            override fun onUserDataEdited() {}

            override fun onError(errorBundle: ErrorBundle?) {}

        }

        editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)
    }

    override fun onDestroy() {
        this.view = null;
    }


}