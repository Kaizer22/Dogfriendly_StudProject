package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.SignInPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.SignInView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.SignInActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*

/**
 * Фрагмент отображающий окно авторизации
 * @author nikolaygorokhov1@gmail.com
 */
class SignInFragment : BaseFragment(), SignInView, View.OnClickListener {

    private var userSignInPresenter: SignInPresenter? = null
    private var email: String? = null
    private var password: String? = null

    override fun initializePresenter() {
        userSignInPresenter = SignInPresenter(AuthManagerFirebaseImpl())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userSignInPresenter?.setView(this)
        button_signin.setOnClickListener(this)
        button_signup.setOnClickListener(this)
    }

    fun auth(){
        email = enter_email.text.toString()
        password = enter_password.text.toString()
        userSignInPresenter?.auth(email, password)
        showLoading()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_signin -> {
                auth()
            }
            R.id.button_signup ->
                (activity as SignInActivity).navigateToUserSignUp()
        }
    }

    override fun onPause() {
        super.onPause()
        userSignInPresenter?.onPause()
    }

    override fun onResume() {
        super.onResume()
        userSignInPresenter?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        userSignInPresenter?.onDestroy()
    }

    override fun showLoading() {
        //TODO("Not yet implemented")
    }

    override fun hideLoading() {
        //TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        //TODO("Not yet implemented")
    }

}