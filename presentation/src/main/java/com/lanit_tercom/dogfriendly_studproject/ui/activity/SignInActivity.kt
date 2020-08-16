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

    //TODO чтобы протестировать ChatFragment в канале -MCqwIrhuEPqkgz1GV18  раскомментите этот
    // код и код в UserSignInPresenter
    fun navigateToChat() =
            navigator?.navigateToChat(this, "-MCqwIrhuEPqkgz1GV18" );

    fun navigateToChannelList(userId: String?)=
            navigator?.navigateToChannelList(this, userId)

    fun navigateToInvitationScreen(userId: String?) =
            navigator?.navigateToInvitationScreen(this, userId)

    override fun initializeActivity(savedInstanceState: Bundle?){
        if (savedInstanceState == null)
            addFragment(R.id.ft_container, SignInFragment())
    }

}