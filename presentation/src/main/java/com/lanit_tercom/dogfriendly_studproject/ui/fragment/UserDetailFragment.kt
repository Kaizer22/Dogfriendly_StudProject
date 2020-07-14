package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UseCaseTemp
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import kotlinx.android.synthetic.main.fragment_user_detail.*

/**
 * Фрагмент отображающий окно пользователя
 * @author nikolaygorokhov1@gmail.com
 * @author prostak.sasha111@mail.ru
 */
class UserDetailFragment(private val userId: Int?) : BaseFragment(), UserDetailsView{

    private var userDetailPresenter: UserDetailPresenter? = null

    override fun initializePresenter() {
        userDetailPresenter = UserDetailPresenter( null, UseCaseTemp())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDetailPresenter?.setView(this)
        val user: UserModel? = userDetailPresenter?.loadUser(userId)
        userDetailPresenter?.renderUser(user)
    }

    override fun onPause() {
        super.onPause()
        userDetailPresenter?.onPause()
    }

    override fun onResume() {
        super.onResume()
        userDetailPresenter?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        userDetailPresenter?.onDestroy()
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

    override fun renderCurrentUser(user: UserModel?) {
        user_id.text = user?.id.toString()
        user_name.text = user?.name
    }
}