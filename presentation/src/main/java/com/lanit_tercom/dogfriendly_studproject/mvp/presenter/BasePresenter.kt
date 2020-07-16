package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.LoadDataView

/**
 * Базовый класс для всех презентеров
 * @author nikolaygorokhov1@gmail.com
 */
abstract class BasePresenter {

    protected var view: LoadDataView? = null

    fun onResume(){
        //TODO("Not yet implemented")
    }

    fun onPause(){
        //TODO("Not yet implemented")
    }

    fun onDestroy(){
        view = null
    }


}