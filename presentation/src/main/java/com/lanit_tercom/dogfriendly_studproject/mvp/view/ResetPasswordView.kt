package com.lanit_tercom.dogfriendly_studproject.mvp.view

/**
 *
 * Интерфейс, который наследуют классы предназначенные для
 * отображения приветственного экрана
 * @author dshebut@rambler.ru
 */
interface ResetPasswordView : LoadDataView{
    fun showResetError(resourceID : Int)

    fun changeCondition(isFinished: Boolean)
}