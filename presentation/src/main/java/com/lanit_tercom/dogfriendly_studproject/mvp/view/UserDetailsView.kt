package com.lanit_tercom.dogfriendly_studproject.mvp.view

import android.view.View
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel

interface UserDetailsView{
    fun renderCurrentUser(user: UserModel?)
}
