package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lanit_tercom.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UseCaseTemp
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserSignInPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserSignInView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*

/**
 * Фрагмент отображающий окно авторизации
 * @author nikolaygorokhov1@gmail.com
 */
class UserSignInFragment : BaseFragment(), UserSignInView {

    private var userSignInPresenter: UserSignInPresenter? = null
    private var email: String? = null
    private var password: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onStart() {
        super.onStart()
        button_signin.setOnClickListener {
            email = enter_email.text.toString()
            password = enter_password.text.toString()
            userSignInPresenter?.auth(email, password)
        }
        button_signup.setOnClickListener {
            toSignUpScreen()
        }
    }

    override fun initializePresenter() {
        userSignInPresenter = UserSignInPresenter(this, AuthManagerFirebaseImpl(), UseCaseTemp())
    }

    override fun toSignUpScreen() =
            (activity as BaseActivity).replaceFragment(R.id.ft_container, UserSignUpFragment())

    override fun toMapScreen() =
            (activity as BaseActivity).replaceFragment(R.id.ft_container, UserMapFragment())

}