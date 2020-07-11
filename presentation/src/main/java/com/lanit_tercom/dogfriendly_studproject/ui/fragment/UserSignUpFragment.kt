package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lanit_tercom.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UseCaseTemp
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserSignUpPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserSignUpView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*

/**
 * Фрагмент отображающий окно регистрации
 * @author nikolaygorokhov1@gmail.com
 * @author prostak.sasha111@mail.ru
 */
class UserSignUpFragment : BaseFragment(), UserSignUpView {

    private var userSignUpPresenter: UserSignUpPresenter? = null
    private var email: String? = null
    private var password: String? = null
    private var password_repeat: String? = null
    private var name: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onStart() {
        super.onStart()
        button_signup.setOnClickListener {
            email = edit_email.text.toString()
            password = edit_password.text.toString()
            name = edit_name.text.toString()
            password_repeat = edit_repeat_password.text.toString()
            if (password == password_repeat){
                userSignUpPresenter?.registerUser(email, password, name)
            } else showToastMessage("Пароли не совпадают!")
        }
    }

    override fun initializePresenter() {
        userSignUpPresenter = UserSignUpPresenter(this, AuthManagerFirebaseImpl(), UseCaseTemp())
    }

    override fun toMapScreen() =
            (activity as BaseActivity).replaceFragment(R.id.ft_container, UserMapFragment())

}