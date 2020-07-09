package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel

/**
 * Класс временно заменяющий данные, поступающие из domain слоя
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UseCaseTemp {
    companion object{
        val users: MutableSet<UserModel> = mutableSetOf()
    }

    init{
        users.add(UserModel(1,"Bob", "bob@gmail.com", "Qwer1234", Point(10.2, 50.3)))
        users.add(UserModel(2, "Martin", "martin@gmail.com", "12345678",Point(20.3, 40.5)))
        users.add(UserModel(3, "Beatrice", "beatrice@gmail.com", "password", Point(30.3, 30.5)))
    }

    fun fillList(list: MutableSet<UserModel>){
        users.forEach {
            list.add(it)
        }
    }

    fun addUser(user: UserModel){
        users.add(user)

    }


}