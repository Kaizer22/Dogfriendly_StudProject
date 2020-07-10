package com.lanit_tercom.dogfriendly_studproject.mvp.presenter


import android.util.Log
import com.lanit_tercom.data.auth_manager.AuthManager
import com.lanit_tercom.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.BaseFragment
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignInFragment
import java.lang.Exception

/**
 * presenter класс для работы с пользователями
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserDetailPresenter(userDetailsView: UserDetailsView) {
    private var viewDetailsView: UserDetailsView = userDetailsView
    val listOfActiveUsers: MutableSet<UserModel> = mutableSetOf()

    private val useCaseTemp = UseCaseTemp()

    val authManager : AuthManager = AuthManagerFirebaseImpl()

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
            try{
                authManager.signInEmail(email, password)
                (viewDetailsView as UserSignInFragment).isRegistered = true
            }catch (e: Exception){
                (viewDetailsView as UserSignInFragment).showToastMessage(e.message)

            }
    }

    fun registerUser(email: String?, password: String?, name: String?){
        authManager.createUserWithEmailPassword(email, password)

        var maxId = UseCaseTemp.users.maxBy { it.id }!!.id
        useCaseTemp.addUser(UserModel(++maxId, name!!, email!!, password!!, Point(21.8, 42.3)))
    }

    /*
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

     */
}