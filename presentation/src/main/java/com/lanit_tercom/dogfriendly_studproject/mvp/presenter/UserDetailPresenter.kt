package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView

class UserDetailPresenter(userDetailsView: UserDetailsView) {
    private val userId: String? = null
    private var viewDetailsView: UserDetailsView = userDetailsView

    //    private fun showUserDetailsInView(userDto: UserDto) {
//        val userModel: UserModel = this.userDtoModelMapper.map2(userDto)
//        viewDetailsView!!.renderCurrentUser(userModel)
//    }

}