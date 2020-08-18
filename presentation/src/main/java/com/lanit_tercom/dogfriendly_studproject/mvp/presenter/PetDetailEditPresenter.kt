package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.net.Uri
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailEditView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.PetDetailEditFragment
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase


class PetDetailEditPresenter(private val userId: String?,
                             private val pushPhotoUseCase: PushPhotoUseCase?,
                             private val deletePhotoUseCase: DeletePhotoUseCase?): BasePresenter(){

    fun setView(view: PetDetailEditView){ this.view = view }

    /**
     * Если локальный uri не null - отправляем в базу аватар
     * Если нет - делаем запрос на удаление имеющегося
     */
    fun setAvatar(pet: PetModel?, avatarUri: Uri?){

        if(avatarUri != null){
            var pushPhotoCallback: PushPhotoUseCase.Callback = object : PushPhotoUseCase.Callback {

                override fun onPhotoPushed(downloadUri: String?) {
                    pet?.avatar = Uri.parse(downloadUri);
                    (view as? PetDetailEditFragment)?.navigateToNext()
                }

                override fun onError(errorBundle: ErrorBundle) {}

            }

            pushPhotoUseCase?.execute(userId+"/"+pet?.id+"/avatar", avatarUri.toString(), pushPhotoCallback)
        } else {

            var deletePhotoCallback: DeletePhotoUseCase.Callback = object : DeletePhotoUseCase.Callback {

                override fun onPhotoDeleted() {
                    pet?.avatar = null
                    (view as? PetDetailEditFragment)?.navigateToNext()
                }

                override fun onError(errorBundle: ErrorBundle) {
                    (view as? PetDetailEditFragment)?.navigateToNext()
                }

            }

            deletePhotoUseCase?.execute(userId+"/"+pet?.id+"/avatar", deletePhotoCallback)
        }
    }



}
