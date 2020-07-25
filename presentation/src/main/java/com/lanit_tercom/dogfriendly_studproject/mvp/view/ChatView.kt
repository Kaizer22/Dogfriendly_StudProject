package com.lanit_tercom.dogfriendly_studproject.mvp.view

import android.view.View

/**
 *
 * Интерфейс, который наследуют классы предназначенные для отображения чата
 * @author dshebut@rambler.ru
*/
interface ChatView : LoadDataView{

    fun showProgressMessage(event : String)

    fun renderMessages()
}