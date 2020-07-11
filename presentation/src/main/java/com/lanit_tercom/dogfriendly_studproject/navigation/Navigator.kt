package com.lanit_tercom.dogfriendly_studproject.navigation

import android.content.Context
import android.content.Intent
import com.lanit_tercom.dogfriendly_studproject.ui.activity.*

/**
 * Класс для перехода к активностям
 * @author prostak.sasha111@mail.ru
 */

class Navigator {
    private var intentToLaunch: Intent? = null
    fun navigateToUserSignIn(context: Context){
        intentToLaunch = UserSignInActivity.getCallingIntent(context)
        context.startActivity(intentToLaunch)
    }

    fun navigateToUserSignUp(context: Context){
        intentToLaunch = UserSignUpActivity.getCallingIntent(context)
        context.startActivity(intentToLaunch)
    }

    fun navigateToUserMap(context: Context){
        intentToLaunch = UserMapActivity.getCallingIntent(context)
        context.startActivity(intentToLaunch)

    }

    fun navigateToUserDetail(context: Context, userId: Int?){
        intentToLaunch = UserDetailActivity.getCallingIntent(context, userId)
        context.startActivity(intentToLaunch)
    }

}
