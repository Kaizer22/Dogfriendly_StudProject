package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView

/**
 * Класс от которого наследуются все фрагменты.
 * Служит для инициализации presenter класса при создании фрагмента
 * Сохраняет состояние фрагмента (при изменении/удалении Activity)
 * @author nikolaygorokhov1@gmail.com
 */
abstract class BaseFragment : Fragment(), UserDetailsView {

    protected var userDetailPresenter: UserDetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        initializePresenter()
        userDetailPresenter?.fillListOfActiveUsers()
    }

    private fun initializePresenter(){
        userDetailPresenter = UserDetailPresenter(this)
    }
    protected fun showToastMessage(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}