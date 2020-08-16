package com.lanit_tercom.dogfriendly_studproject.mvp.model

import android.net.Uri

/**
 * Класс представляющий модель пользователя
 * @author nikolaygorokhov1@gmail.com
 */
data class UserModel(var id: String?,
                     var name: String?,
                     var age: Int?,
                     var email: String?,
                     var password: String?,
                     var about: String?,
                     var plans: String?,
                     var avatar: Uri?,
                     var pets: List<PetModel>?,
                     var point: Point?){
    constructor() : this(null, null, null, null, null, null, null, null, null, null)
}