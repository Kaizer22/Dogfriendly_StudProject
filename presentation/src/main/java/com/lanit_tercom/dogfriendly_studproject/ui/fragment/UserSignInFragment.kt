package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*

/**
 * Фрагмент отображающий окно авторизации
 * @author nikolaygorokhov1@gmail.com
 */
class UserSignInFragment : BaseFragment(), UserDetailsView {
    private var email: String? = null
    private var password: String? = null
    var isRegistered: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onStart() {
        super.onStart()
        button_signin.setOnClickListener {
            userDetailPresenter?.fillListOfActiveUsers()
            email = enter_email.text.toString()
            password = enter_password.text.toString()
            isRegistered = false
            userDetailPresenter?.auth(email, password)
            if (isRegistered){
                (activity as BaseActivity).replaceFragment(R.id.ft_container, UserMapFragment())
            }
        }
        button_signup.setOnClickListener {
            (activity as BaseActivity).replaceFragment(R.id.ft_container, UserSignUpFragment())
        }
    }

    override fun renderCurrentUser(user: UserModel?) {}



}