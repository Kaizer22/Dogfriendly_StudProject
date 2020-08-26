package com.lanit_tercom.dogfriendly_studproject.mvp.view

import android.view.View
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel

/**
 *
 * Интерфейс, который наследуют классы предназначенные для отображения чата
 * @author dshebut@rambler.ru
*/
interface ChatView : LoadDataView{

    fun showProgressMessage(event : String)

    fun renderMessages()

    fun showMessageMenu(message : MessageModel, position : Int, targetView : View)

    fun updateChannelInfo(name : String, status: String)
}