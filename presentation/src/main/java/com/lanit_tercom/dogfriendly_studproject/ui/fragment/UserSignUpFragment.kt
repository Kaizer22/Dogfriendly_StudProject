package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserSignUpPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserSignUpView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserSignUpActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.button_sign_up

/**
 * Фрагмент отображающий окно регистрации
 * @author nikolaygorokhov1@gmail.com
 * @author prostak.sasha111@mail.ru
 */
class UserSignUpFragment : BaseFragment(), UserSignUpView, View.OnClickListener {

    private var userSignUpPresenter: UserSignUpPresenter? = null
    private var email: String? = null
    private var password: String? = null
    private var passwordRepeat: String? = null
    private var name: String? = null

    override fun initializePresenter() {
        userSignUpPresenter = UserSignUpPresenter(AuthManagerFirebaseImpl())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userSignUpPresenter?.setView(this)
        button_sign_up.setOnClickListener(this)
        to_sign_in_link.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_sign_up ->{
                email = edit_email.text.toString()
                password = edit_password.text.toString()
                name = edit_name.text.toString()
                //passwordRepeat = edit_repeat_password.text.toString()
                //if (password == passwordRepeat) {
                userSignUpPresenter?.registerUser(email, password)
                (activity as UserSignUpActivity).navigateToUserSignIn()
                //} else
                    //showToastMessage("Пароли не совпадают!")
            }
            R.id.to_sign_in_link -> (activity as UserSignUpActivity).navigateToUserSignIn()
        }
    }

    override fun onPause() {
        super.onPause()
        userSignUpPresenter?.onPause()
    }

    override fun onResume() {
        super.onResume()
        userSignUpPresenter?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        userSignUpPresenter?.onDestroy()
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        TODO("Not yet implemented")
    }

}