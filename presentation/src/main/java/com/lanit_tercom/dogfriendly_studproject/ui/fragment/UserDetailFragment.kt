package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import kotlinx.android.synthetic.main.fragment_user_detail.*


/**
 * Фрагмент отображающий окно пользователя
 * @author nikolaygorokhov1@gmail.com
 */

class UserDetailFragment : BaseFragment(), UserDetailsView{

    private var user: UserModel? = null
    private var userDetailPresenter: UserDetailPresenter? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        userDetailPresenter?.fillListOfActiveUsers()
        userDetailPresenter?.renderUser(user?.id)
    }

    fun attachUser(user: UserModel?){
        this.user = user
    }

    override fun initializePresenter(){
        userDetailPresenter = UserDetailPresenter(this)}

    override fun renderCurrentUser(user: UserModel?) {
        user_id.text = user?.id.toString()
        user_name.text = user?.name
    }

}