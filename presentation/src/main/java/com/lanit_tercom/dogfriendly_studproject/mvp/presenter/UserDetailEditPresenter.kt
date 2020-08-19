package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.net.Uri
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailEditView
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase

class UserDetailEditPresenter(private val editUserDetailsUseCase: EditUserDetailsUseCase?,
                              private val pushPhotoUseCase: PushPhotoUseCase?,
                              private val deletePhotoUseCase: DeletePhotoUseCase?): BasePresenter() {

    private var mapper = UserDtoModelMapper()

    fun setView(view: UserDetailEditView){ this.view = view }

    /**
     * Это функция только для экрана редактирования "о себе" и "планы на прогулку".
     */
    fun editTextFields(user: UserModel?) {
        val editUserDetailsCallback: EditUserDetailsUseCase.Callback = object : EditUserDetailsUseCase.Callback {

            override fun onUserDataEdited() {}

            override fun onError(errorBundle: ErrorBundle?) {}

        }

        editUserDetailsUseCase?.execute(mapper.map1(user), editUserDetailsCallback)
    }

    /**
     * А это уже для редактирования оставльных частей модели.
     * Если avatar null - удаляем фото, если не null - добавляем.
     */
    fun editUserDetails(user: UserModel?, avatarUri: Uri?){

        val editUserDetailsCallback: EditUserDetailsUseCase.Callback = object : EditUserDetailsUseCase.Callback {

            override fun onUserDataEdited() {(view as? UserDetailEditView)?.navigateBack()}

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

                override fun onError(errorBundle: ErrorBundle) {}

            }

            deletePhotoUseCase?.execute(user?.id+"/avatar", deletePhotoCallback)
        }
    }

}
