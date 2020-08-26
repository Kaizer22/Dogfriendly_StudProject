package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.EditTextView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.EditTextFragment
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase

class EditTextPresenter(private val editUserDetailsUseCase: EditUserDetailsUseCase?): BasePresenter() {
    private var view: EditTextView? = null
    private var userId: String? = null
    private var mapper = UserDtoModelMapper()

    //Загрузка модели юзера, тексты которой будем редачит
    fun initialize(userId: String?) {
        this.userId = userId
    }

    fun setView(view: EditTextView){ this.view = view }

    fun editTextFields(user: UserModel?) {
        val editUserDetailsCallback: EditUserDetailsUseCase.Callback = object : EditUserDetailsUseCase.Callback {

            override fun onUserDataEdited() {
                (view as? EditTextFragment)?.navigateBack()
            }

            override fun onError(errorBundle: ErrorBundle?) {}

        }

        editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)
    }

    override fun onDestroy() { this.view = null }

}