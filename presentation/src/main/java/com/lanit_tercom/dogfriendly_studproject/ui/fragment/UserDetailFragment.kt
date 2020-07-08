package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView

/*
По минимуму скопировано с примера
 */
class UserDetailFragment : BaseFragment(), UserDetailsView{
    private val id: String? = null
    private var userDetailPresenter: UserDetailPresenter? = null

    private var user_id: TextView? = null
    private var user_name: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val fragmentView: View = inflater.inflate(R.layout.fragment_user_detail, container, false)


        this.user_id = fragmentView.findViewById(R.id.user_id)
        this.user_name = fragmentView.findViewById(R.id.user_name)

        return fragmentView
    }

    override fun initializePresenter() {
        userDetailPresenter = UserDetailPresenter(this)
    }

    override fun renderCurrentUser(user: UserModel?) {
        if (user != null) {
            this.user_id?.text = user.id
            this.user_name?.text = user.name
        }
    }

}