package com.lanit_tercom.dogfriendly_studproject.mvp.presenter


import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignInFragment

/**
 * presenter класс для работы с пользователями
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserDetailPresenter(userDetailsView: UserDetailsView) {
    private var viewDetailsView: UserDetailsView = userDetailsView
    val listOfActiveUsers: MutableSet<UserModel> = mutableSetOf()
    private val useCaseTemp = UseCaseTemp()

    fun fillListOfActiveUsers() =
            useCaseTemp.fillList(listOfActiveUsers)

    fun renderMap() =
            listOfActiveUsers.forEach {
            viewDetailsView.renderCurrentUser(it)}

    fun renderUser(id: Int?){
        val user = listOfActiveUsers.find { it.id == id }
        viewDetailsView.renderCurrentUser(user)
    }

    fun auth(email: String?, password: String?){
        if (viewDetailsView is UserSignInFragment)
            listOfActiveUsers.forEach {
                if(it.email == email && it.password == password)
                    (viewDetailsView as UserSignInFragment).isRegistered = true}
    }

    fun registerUser(email: String?, password: String?, name: String?){
        var maxId = UseCaseTemp.users.maxBy { it.id }!!.id
        useCaseTemp.addUser(UserModel(++maxId, name!!, email!!, password!!, Point(21.8, 42.3)))
    }
}