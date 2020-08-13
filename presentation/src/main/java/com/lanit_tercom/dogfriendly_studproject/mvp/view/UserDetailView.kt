package com.lanit_tercom.dogfriendly_studproject.mvp.view

import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel

/**
 * Интерфейс, который наследуют все классы работающие с отображением пользователя
 * @author nikolaygorokhov1@gmail.com
 */
interface UserDetailView : LoadDataView{

    fun renderCurrentUser(user: UserModel?)

}
