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

    fun navigateToUserSignIn(context: Context) =
        context.startActivity(
                UserSignInActivity.getCallingIntent(context))

    fun navigateToUserSignUp(context: Context) =
        context.startActivity(
                UserSignUpActivity.getCallingIntent(context))

    fun navigateToUserMap(context: Context) =
        context.startActivity(
                UserMapActivity.getCallingIntent(context))

    fun navigateToUserDetail(context: Context, userId: String?) =
        context.startActivity(
                UserDetailActivity.getCallingIntent(context, userId))

}
