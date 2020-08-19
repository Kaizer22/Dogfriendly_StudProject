package com.lanit_tercom.dogfriendly_studproject.mvp.view

import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel

/**
 * Интерфейс, который наследуют все классы работающие с картой
 * @author prostak.sasha111@mail.ru
 */
interface MapView : LoadDataView{

    fun renderUserOnMap(userId: String?, latitude: Double?, longitude: Double?)

}