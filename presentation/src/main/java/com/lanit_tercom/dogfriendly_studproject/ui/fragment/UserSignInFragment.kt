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
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserSignInActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*

/**
 * Фрагмент отображающий окно авторизации
 * @author nikolaygorokhov1@gmail.com
 */
class UserSignInFragment : BaseFragment(), UserSignInView, View.OnClickListener {

    private var userSignInPresenter: UserSignInPresenter? = null
    private var email: String? = null
    private var password: String? = null

    override fun initializePresenter() {
        userSignInPresenter = UserSignInPresenter(null, UseCaseTemp())
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

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.button_signin -> {
                email = enter_email.text.toString()
                password = enter_password.text.toString()
                userSignInPresenter?.auth(email, password)
            }
            R.id.button_signup -> {
                (activity as UserSignInActivity).navigateToUserSignUp()
            }
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
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        TODO("Not yet implemented")
    }


}