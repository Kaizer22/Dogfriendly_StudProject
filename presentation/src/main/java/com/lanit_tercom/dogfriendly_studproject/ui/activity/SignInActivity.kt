package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.SignInFragment

/**
 * Активность авторизации.
 * Запускает фрагмент с авторизацией.
 * @author prostak.sasha111@mail.ru
 */
class SignInActivity : BaseActivity() {

    companion object{

        fun getCallingIntent(context: Context): Intent =
            Intent(context, SignInActivity::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    fun navigateToUserSignUp() =
        navigator?.navigateToUserSignUp(this)

    fun navigateToUserMap() =
        navigator?.navigateToUserMap(this)

    fun navigateToResetPassword() =
            navigator?.navigateToResetPassword(this)

    fun navigateToWelcomeScreen() =
            navigator?.navigateToWelcomeScreen(this)


    //fun navigateToChat() =
            //navigator?.navigateToChat(this, "-MCqwIrhuEPqkgz1GV18" )

    //fun navigateToChannelList(userID: String) =
            //navigator?.navigateToChannelList(this, userID)

    fun navigateToMainNavigation() =
            navigator?.navigateToMainNavigation(this)

    fun navigateToChannelList(userId: String?)=
            navigator?.navigateToChannelList(this, userId)

    fun navigateToUserDetail(userId: String?)=
            navigator?.navigateToUserDetail(this, userId)



    fun navigateToInvitationScreen(userId: String?) =
            navigator?.navigateToInvitationScreen(this, userId)

    override fun initializeActivity(savedInstanceState: Bundle?){
        if (savedInstanceState == null)
            addFragment(R.id.ft_container, SignInFragment())
    }

}