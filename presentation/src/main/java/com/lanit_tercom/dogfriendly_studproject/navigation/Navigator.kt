package com.lanit_tercom.dogfriendly_studproject.navigation

import android.content.Context
import android.content.Intent
import com.lanit_tercom.dogfriendly_studproject.ui.activity.*

/**
 * Класс для перехода к активностям
 * @author prostak.sasha111@mail.ru
 * @author dshebut@rambler.ru
 */
class Navigator {
    private var intentToLaunch: Intent? = null

    fun navigateToUserSignIn(context: Context) =
        context.startActivity(
                SignInActivity.getCallingIntent(context))

    fun navigateToUserSignUp(context: Context) =
        context.startActivity(
                SignUpActivity.getCallingIntent(context))

    fun navigateToUserMap(context: Context) =
        context.startActivity(
                MapActivity.getCallingIntent(context))

    fun navigateToUserDetail(context: Context, userId: String?) =
        context.startActivity(
                UserDetailActivity.getCallingIntent(context, userId))

    fun navigateToChat(context: Context, channelID : String?) =
            context.startActivity(
                    ChatActivity.getCallingIntent(context, channelID))

}
