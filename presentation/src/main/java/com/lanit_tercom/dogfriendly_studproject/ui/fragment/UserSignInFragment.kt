package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.maps.SupportMapFragment
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
    var email: String? = null
    var password: String? = null
    var isRegistered: Boolean = false

    private var userDetailPresenter: UserDetailPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_sign_in, container, false)

        view.findViewById<Button>(R.id.button_signin).setOnClickListener {
            userDetailPresenter?.fillListOfActiveUsers()
            email = view.findViewById<EditText>(R.id.enter_email).text.toString()
            password = view.findViewById<EditText>(R.id.enter_password).text.toString()
            userDetailPresenter?.auth()

            if(isRegistered){
                val mapFragment = UserMapFragment()
                /*
                TODO: тут ошибка, надо чтобы при вводе правильного email и пароля открывалась карта
                не работает
                //childFragmentManager.beginTransaction().add(R.id.activity_main, mapFragment).commit()
                //(activity as BaseActivity).replaceFragment(R.layout.activity_main, mapFragment)
                 */

            }
        }

        return view
    }


    override fun onStart() {
        super.onStart()

    }

    override fun initializePresenter(){
        userDetailPresenter = UserDetailPresenter(this)}

    override fun renderCurrentUser(user: UserModel?) {}




}