package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.net.Uri
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailEditView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase

class UserDetailEditPresenter(private val getUserDetailsUseCase: GetUserDetailsUseCase?,
                              private val editUserDetailsUseCase: EditUserDetailsUseCase?,
                              private val pushPhotoUseCase: PushPhotoUseCase?,
                              private val deletePhotoUseCase: DeletePhotoUseCase?): BasePresenter() {

    private var userId: String? = null
    var user: UserModel? = null
    private var view: UserDetailEditView? = null
    private var mapper = UserDtoModelMapper()

    fun initialize(userId: String?) {
        this.userId = userId
        this.loadUserDetails()
    }


    fun setView(view: UserDetailEditView){ this.view = view }

    private fun loadUserDetails() =
            getUserDetailsUseCase?.execute(userId, userDetailsCallback)

    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {

        override fun onUserDataLoaded(userDto: UserDto?){
            user = mapper.map2(userDto)
            (view as UserDetailEditView).renderCurrentUser(user)
        }


        override fun onError(errorBundle: ErrorBundle?) {}

    }

    fun editUserDetails(avatarUri: Uri?){

        val editUserDetailsCallback: EditUserDetailsUseCase.Callback = object : EditUserDetailsUseCase.Callback {

            override fun onUserDataEdited() {
                view?.navigateBack()
            }

            override fun onError(errorBundle: ErrorBundle?) {}

        }


        if(avatarUri != null){
            val pushPhotoCallback: PushPhotoUseCase.Callback = object : PushPhotoUseCase.Callback {

                override fun onPhotoPushed(downloadUri: String?) {
                    user?.avatar = Uri.parse(downloadUri)
                    editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)
                }

                override fun onError(errorBundle: ErrorBundle) {}

            }

            pushPhotoUseCase?.execute(user?.id+"/avatar", avatarUri.toString(), pushPhotoCallback)
        } else {

            val deletePhotoCallback: DeletePhotoUseCase.Callback = object : DeletePhotoUseCase.Callback {

                override fun onPhotoDeleted() {
                    user?.avatar = null
                    editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)
                }

                override fun onError(errorBundle: ErrorBundle) {
                    editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)
                }

            }

            if(user?.avatar != null){
                deletePhotoUseCase?.execute(user?.avatar.toString(), deletePhotoCallback)
            } else {
                editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)
            }


        }
    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }
}
