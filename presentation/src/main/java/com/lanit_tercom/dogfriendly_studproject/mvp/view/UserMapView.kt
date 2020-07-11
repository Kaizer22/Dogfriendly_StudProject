package com.lanit_tercom.dogfriendly_studproject.mvp.view

import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel

/**
 * Интерфейс, который наследуют все классы работающие с картой
 * @author prostak.sasha111@mail.ru
 */
interface UserMapView : LoadDataView{

    fun renderUserOnMap(user: UserModel?)
    fun toDetailScreen(id: Int)

}