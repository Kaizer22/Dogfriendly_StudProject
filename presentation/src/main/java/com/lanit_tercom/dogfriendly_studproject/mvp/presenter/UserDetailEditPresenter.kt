package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.net.Uri
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailEditView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailEditFragment
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

    //Функции вызывающие соответствующие callbacks
    fun editUserDetails(user: UserModel?){

        val editUserDetailsCallback: EditUserDetailsUseCase.Callback = object : EditUserDetailsUseCase.Callback {

            override fun onUserDataEdited() {}

            override fun onError(errorBundle: ErrorBundle?) {}

        }

        editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)

    }

    /**
     * НАДО ЧТОБЫ ТУТ В downloadUri БЫЛА ССЫЛКА НА АВАТАР
     */
    fun setAvatar(userModel: UserModel?, uriString: String?){
       var pushPhotoCallback: PushPhotoUseCase.Callback = object : PushPhotoUseCase.Callback {

            override fun onPhotoPushed(downloadUri: String?) {
                userModel?.avatar = Uri.parse(downloadUri)
            }

            override fun onError(errorBundle: ErrorBundle) {}

        }

        pushPhotoUseCase?.execute(userModel?.id+"/avatar", uriString, pushPhotoCallback)

    }


    fun deleteAvatar(userModel: UserModel){

        var deletePhotoCallback: DeletePhotoUseCase.Callback = object : DeletePhotoUseCase.Callback {

            override fun onPhotoDeleted() {
                userModel.avatar = null
            }

            override fun onError(errorBundle: ErrorBundle) {}

        }

        deletePhotoUseCase?.execute(userModel.id+"/avatar", deletePhotoCallback)
    }

}
