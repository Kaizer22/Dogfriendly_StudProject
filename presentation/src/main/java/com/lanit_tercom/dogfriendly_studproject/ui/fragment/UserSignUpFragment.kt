package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView

/**
 * Фрагмент отображающий окно регистрации
 * @author nikolaygorokhov1@gmail.com
 */
class UserSignUpFragment : BaseFragment(), UserDetailsView {

    private var userDetailPresenter: UserDetailPresenter? = null

    override fun initializePresenter() {
        TODO("Not yet implemented")
    }

    override fun renderCurrentUser(user: UserModel) {
        TODO("Not yet implemented")
    }
}