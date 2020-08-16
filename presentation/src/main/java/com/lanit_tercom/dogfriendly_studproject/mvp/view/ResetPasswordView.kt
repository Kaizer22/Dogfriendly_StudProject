package com.lanit_tercom.dogfriendly_studproject.mvp.view

/**
 *
 * Интерфейс, который наследуют классы предназначенные для
 * отображения экрана сброса пароля
 * @author dshebut@rambler.ru
 */
interface ResetPasswordView : LoadDataView{
    fun showResetError(resourceID : Int)

    fun changeViewCondition(isFinished: Boolean)
}