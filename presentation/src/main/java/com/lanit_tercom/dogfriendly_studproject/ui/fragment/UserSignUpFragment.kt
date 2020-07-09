package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView

/**
 * Фрагмент отображающий окно регистрации
 * @author nikolaygorokhov1@gmail.com
 */
class UserSignUpFragment : BaseFragment(), UserDetailsView {

    private var userDetailPresenter: UserDetailPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_sign_up, container, false)
        return view
    }

    override fun initializePresenter(){
        userDetailPresenter = UserDetailPresenter(this)}

    override fun renderCurrentUser(user: UserModel?) {

    }
}