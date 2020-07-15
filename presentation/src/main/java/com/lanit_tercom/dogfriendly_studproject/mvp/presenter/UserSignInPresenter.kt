package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.util.Log
import com.lanit_tercom.data.auth_manager.AuthManager
import com.lanit_tercom.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserSignInView
import com.lanit_tercom.dogfriendly_studproject.navigation.Navigator
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserSignInActivity
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserSignInFragment

/**
 * presenter класс для работы с авторизацией
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserSignInPresenter(private val authManager: AuthManager?, private val useCaseTemp: UseCaseTemp) : BasePresenter() {

    fun setView(view: UserSignInView) {
        this.view = view
    }

    /*
    Короче, пока с сервера придет подтверждение того вошел юзер или нет, проходит 1с+ времени
    А код идет дальше...
     */
    fun auth(email: String?, password: String?) {
        try {
            Log.i("AUTH_MANAGER", "sign in button is pressed")
            //authManager?.signOut()
           // authManager?.signInEmail(email, password)
            //val b : Boolean? = authManager?.isSignedIn
            //if(b!!) Navigator().navigateToUserMap((view as UserSignInFragment).requireContext())

            //Это сохраним пока, не поймем как правильно сделать взаимодействие
            if (view is UserSignInFragment)
                loadUsers().forEach {
                    if(it.email == email && it.password == password)
                        ((view as UserSignInFragment).activity as UserSignInActivity).navigateToUserMap()}

        } catch (e: Exception) {
            (view as UserSignInFragment).showToastMessage(e.message)
        }
    }



}