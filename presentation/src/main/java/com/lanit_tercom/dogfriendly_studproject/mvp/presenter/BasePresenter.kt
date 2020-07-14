package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.LoadDataView
import com.lanit_tercom.domain.interactor.get.GetUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.UserRepository

/**
 * Базовый класс для всех презентеров
 * @author nikolaygorokhov1@gmail.com
 */
abstract class BasePresenter(protected var repo: UserRepository? = null) {

    protected var view: LoadDataView? = null

    open fun onResume(){
        //TODO("Not yet implemented")
    }

    open fun onPause(){
        //TODO("Not yet implemented")
    }

    open fun onDestroy(){
        view = null
    }

    open fun loadUser(id: String?) : UserModel?{
        return UseCaseTemp.users.find { it.id == id }
    }

    fun loadUsers() : MutableList<UserModel>{
        return UseCaseTemp.users
    }

}
