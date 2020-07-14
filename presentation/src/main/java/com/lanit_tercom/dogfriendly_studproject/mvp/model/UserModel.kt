package com.lanit_tercom.dogfriendly_studproject.mvp.model

/**
 * Класс представляющий модель пользователя
 * @author nikolaygorokhov1@gmail.com
 */
data class UserModel(val id: String, val name: String, val email: String, val password: String, val point: Point) {
}