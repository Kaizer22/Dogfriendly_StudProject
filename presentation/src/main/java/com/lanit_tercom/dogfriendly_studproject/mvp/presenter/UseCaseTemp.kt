package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel

class UseCaseTemp {
    fun fillList(list: MutableList<UserModel>){
        list.add(UserModel(1,"Bob", Point(100.2, 50.3)))
        list.add(UserModel(2, "Martin", Point(20.3, 40.5)))
        list.add(UserModel(3, "Beatrice", Point(-33.852, 151.211)))

    }
}