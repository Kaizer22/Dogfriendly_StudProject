package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.SignInPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.SignInView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.SignInActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*

/**
 * Фрагмент отображающий окно авторизации
 * @author nikolaygorokhov1@gmail.com
 * @author dshebut@rambler.ru
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
        button_sign_in.setOnClickListener(this)
        to_reset_password_link.setOnClickListener(this)
        button_back_to_welcome_screen.setOnClickListener(this)

        view.findViewById<LinearLayout>(R.id.fragment_sign_in).setOnClickListener{ hideKeyboard()}
        //button_signup.setOnClickListener(this)
    }

    fun auth(){
        email = enter_email.text.toString()
        password = enter_password.text.toString()
        if (email != "" && password != ""){
            userSignInPresenter?.auth(email, password)
            showLoading()
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager: InputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_sign_in -> {
                auth()
            }
            R.id.button_sign_up -> {
                (activity as SignInActivity).navigateToUserSignUp()
            }
            R.id.to_reset_password_link -> {
                (activity as SignInActivity).navigateToResetPassword()
            }
            R.id.button_back_to_welcome_screen ->
                (activity as SignInActivity).navigateToWelcomeScreen()
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