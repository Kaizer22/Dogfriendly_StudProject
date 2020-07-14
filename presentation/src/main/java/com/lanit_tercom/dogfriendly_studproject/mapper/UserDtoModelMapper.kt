package com.lanit_tercom.dogfriendly_studproject.mapper

import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.domain.dto.UserDto
import kotlin.random.Random

/**
 * mapper для преобразования UserDto <-> UserMap
 * @author nikolaygorokhov1@gmail.com
 */
class UserDtoModelMapper {
    fun map1(userModel: UserModel?): UserDto? {
        if (userModel == null) { return null }
        val id = userModel.id
        val name = userModel.name
        return UserDto(id, name)
    }

    fun map2(userDto: UserDto?): UserModel? {
        if (userDto == null) { return null }
        val id = userDto.id
        val name = userDto.name
        val point = Point(Random.nextDouble(20.0,50.0), Random.nextDouble(20.0, 50.0))
        return UserModel(id, name, point = point, email = "martin@gmail.com", password = "12345678")
    }
}