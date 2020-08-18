package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.net.Uri
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailEditView
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase

/**
 * Презентер с функцией изменения юзера
 */
class UserDetailEditPresenter(private val editUserDetailsUseCase: EditUserDetailsUseCase?,
                              private val pushPhotoUseCase: PushPhotoUseCase?,
                              private val deletePhotoUseCase: DeletePhotoUseCase?): BasePresenter() {

    private var mapper = UserDtoModelMapper()

    fun setView(view: UserDetailEditView){ this.view = view }

    fun editTextFields(user: UserModel?) {
        val editUserDetailsCallback: EditUserDetailsUseCase.Callback = object : EditUserDetailsUseCase.Callback {

            override fun onUserDataEdited() {}

            override fun onError(errorBundle: ErrorBundle?) {}

        }

        editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)
    }

    fun editUserDetails(user: UserModel?, avatarUri: Uri?){

        val editUserDetailsCallback: EditUserDetailsUseCase.Callback = object : EditUserDetailsUseCase.Callback {

            override fun onUserDataEdited() {(view as? UserDetailEditView)?.navigateBack()}

            override fun onError(errorBundle: ErrorBundle?) {}

        }


        if(avatarUri != null){
            var pushPhotoCallback: PushPhotoUseCase.Callback = object : PushPhotoUseCase.Callback {

                override fun onPhotoPushed(downloadUri: String?) {
                    user?.avatar = Uri.parse(downloadUri)
                    editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)
                }

                override fun onError(errorBundle: ErrorBundle) {}

            }

            pushPhotoUseCase?.execute(user?.id+"/avatar", avatarUri.toString(), pushPhotoCallback)
        } else {

            var deletePhotoCallback: DeletePhotoUseCase.Callback = object : DeletePhotoUseCase.Callback {

                override fun onPhotoDeleted() {
                    user?.avatar = null
                    editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)
                }

                override fun onError(errorBundle: ErrorBundle) {}

            }

            deletePhotoUseCase?.execute(user?.id+"/avatar", deletePhotoCallback)
        }
    }

}
