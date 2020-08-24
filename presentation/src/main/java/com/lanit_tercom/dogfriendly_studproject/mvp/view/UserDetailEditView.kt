package com.lanit_tercom.dogfriendly_studproject.mvp.view

import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel

interface UserDetailEditView: LoadDataView {
    fun renderCurrentUser(user: UserModel?)
    fun navigateBack()
}