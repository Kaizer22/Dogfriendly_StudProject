package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import kotlinx.android.synthetic.main.fragment_user_detail.*


/**
 * Фрагмент отображающий окно пользователя
 * @author nikolaygorokhov1@gmail.com
 * @author prostak.sasha111@mail.ru
 */

class UserDetailFragment : BaseFragment(){

    private var user: UserModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        userDetailPresenter?.renderUser(user?.id)
    }

    fun attachUser(user: UserModel?){ this.user = user }

    override fun renderCurrentUser(user: UserModel?) {
        user_id.text = user?.id.toString()
        user_name.text = user?.name
    }

}