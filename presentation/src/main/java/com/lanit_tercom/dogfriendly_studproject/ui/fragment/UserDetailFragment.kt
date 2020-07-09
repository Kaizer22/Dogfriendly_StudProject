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


/**
 * Фрагмент отображающий окно пользователя
 * @author nikolaygorokhov1@gmail.com
 */
//TODO: Тут вообще все неправильно и не работает, саня помоги(((
class UserDetailFragment : BaseFragment(), UserDetailsView{

    private var user: UserModel? = null
    private var userDetailPresenter: UserDetailPresenter? = null

    private var user_id: TextView? = null
    private var user_name: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView: View = inflater.inflate(R.layout.fragment_user_detail, container, false)

        this.user_id = fragmentView.findViewById(R.id.user_id)
        this.user_name = fragmentView.findViewById(R.id.user_name)

        userDetailPresenter?.renderUser(user!!.id)
        return fragmentView
    }

    fun attachUser(user: UserModel){
        this.user = user
    }

    override fun initializePresenter(){
        userDetailPresenter = UserDetailPresenter(this)}

    override fun renderCurrentUser(user: UserModel) {
        this.user_id?.text = user.id.toString()
        this.user_name?.text = user.name
    }

}