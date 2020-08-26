package com.lanit_tercom.dogfriendly_studproject.navigation

import android.content.Context
import android.content.Intent
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel
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
        //context.startActivity(
                //UserSignUpActivity.getCallingIntent(context))
        context.startActivity(
                TestSignUpActivity.getCallingIntent(context)
        )

    fun navigateToUserMap(context: Context) =
        context.startActivity(
                MapActivity.getCallingIntent(context))

    fun navigateToUserDetail(context: Context, userId: String?) =
        context.startActivity(
                UserDetailActivity.getCallingIntent(context, userId))

    fun navigateToChat(context: Context, channelModel : ChannelModel) =
            context.startActivity(
                    ChatActivity.getCallingIntent(context,
                            channelModel.id, channelModel.name, channelModel.members))

    fun navigateToResetPassword(context: Context) =
            context.startActivity(
                    ResetPasswordActivity.getCallingIntent(context)
            )
    fun navigateToWelcomeScreen(context: Context) =
            context.startActivity(
                    WelcomeActivity.getCallingIntent(context)
            )

    fun navigateToMainNavigation(context: Context) =
            context.startActivity(
                    MainNavigationActivity.getCallingIntent(context)
            )

    fun navigateToChannelList(context: Context, userID: String?) =
            context.startActivity(
                    ChannelListActivity.getCallingIntent(context, userID)
            )

    fun navigateToInvitationScreen(context: Context, userId: String?) =
            context.startActivity(
                    InvitationActivity.getCallingIntent(context, userId)
            )

    fun navigateToWalkDetails(context: Context, userId: String?) =
            context.startActivity(
                    WalkActivity.getCallingIntent(context, userId)
            )

    fun navigateToUserDetailObserver(context: Context, hostId: String, checkedUserId: String) {
        context.startActivity(
                UserDetailObserverActivity.getCallingIntent(context, hostId, checkedUserId)
        )
    }

}
