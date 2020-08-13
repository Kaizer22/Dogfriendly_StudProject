package com.lanit_tercom.dogfriendly_studproject.navigation

import android.content.Context
import android.content.Intent
import com.lanit_tercom.dogfriendly_studproject.ui.activity.*
import com.lanit_tercom.dogfriendly_studproject.ui.activity.user_detail.UserDetailActivity

/**
 * Класс для перехода к активностям
 * @author prostak.sasha111@mail.ru
 * @author dshebut@rambler.ru
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

    fun navigateToChat(context: Context, channelID : String?) =
            context.startActivity(
                    ChatActivity.getCallingIntent(context, channelID))

    fun navigateToChannelList(context: Context, userID: String?) =
            context.startActivity(
                    ChannelListActivity.getCallingIntent(context, userID)
            )

}
