package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mvp.view.LoadDataView
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserSignUpView

/*
 вызывайте setView из соответствующего фрагмента из метода onViewCreated.

Сделайте базовый presenter, задайте в нем lifecycle методы pause, resume, destroy. Наследуйте каждый презентер от него,
 реализуйте эти методы, в destroy обнуляйте ссылку на view.
 Во фрагменте свяжите lifecycle с перезентером, т.е. в соответствующих методах вызывайте функции презентере (onResume, onPause, onDestroy).
 */
abstract class BasePresenter {

    protected var view: LoadDataView? = null

    fun onResume(){
        //TODO: што тут делоть?
    }

    fun onPause(){
        //TODO: што тут делоть?
    }

    fun onDestroy(){
        view = null
    }

}