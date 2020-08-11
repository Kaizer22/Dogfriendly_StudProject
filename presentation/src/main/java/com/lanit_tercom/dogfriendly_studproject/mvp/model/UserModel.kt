package com.lanit_tercom.dogfriendly_studproject.mvp.model

import android.net.Uri

/**
 * Класс представляющий модель пользователя
 * @author nikolaygorokhov1@gmail.com
 */
data class UserModel(var id: String,
                     val name: String,
                     val age: Int,
                     val email: String,
                     val password: String,
                     val about: String,
                     val plans: String,
                     val avatar: Uri,
                     val pets: List<PetModel>,
                     val point: Point)