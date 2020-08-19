package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.net.Uri
import com.lanit_tercom.dogfriendly_studproject.mapper.PetDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailEditView
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoArrayUseCase
import com.lanit_tercom.domain.interactor.user.AddPetUseCase
import java.util.ArrayList


class PetPhotoPresenter(private val addPetUseCase: AddPetUseCase?, private val deletePhotoUseCase: DeletePhotoUseCase, private val pushPhotoArrayUseCase: PushPhotoArrayUseCase) : BasePresenter() {

    private var userId: String? = null
    private val mapper = PetDtoModelMapper()

    fun initialize(userId: String?) {
        this.userId = userId
    }

    fun setView(view: PetDetailEditView) {
        this.view = view
    }

    val addPetCallback: AddPetUseCase.Callback = object : AddPetUseCase.Callback {

        override fun onPetAdded() {
            (view as PetDetailEditView).navigateToNext()
        }

        override fun onError(errorBundle: ErrorBundle?) {}

    }

    /**
     * Если pet.photo == null - сначала отправляем фото, затем присваеваем модели массив ссылок на фото и уже потом пушим ее в бд
     * Иначе (если редачим существующего юзера уже с фотками) - стираем старые фото и добавляем новые, как если бы pet.photo было null.
     * Пока недотестировано.
     */
    fun addPet(pet: PetModel?, uriStrings: ArrayList<String>) {

        val pushPhotoArrayCallback: PushPhotoArrayUseCase.Callback = object : PushPhotoArrayUseCase.Callback {

            override fun onPhotoArrayPushed(downloadUris: ArrayList<String>?) {
                val photos = ArrayList<Uri>()
                for(downloadUri in downloadUris!!) photos.add(Uri.parse(downloadUri))
                pet?.photos = photos
                addPetUseCase?.execute(userId, mapper.map1(pet), addPetCallback)

            }

            override fun onError(errorBundle: ErrorBundle?) {}

        }


        if(pet?.photos.isNullOrEmpty()){
            pushPhotoArrayUseCase.execute(userId+"/"+pet?.id+"/pet_photos", uriStrings, pushPhotoArrayCallback)
        } else {
            val deletePhotoCallback: DeletePhotoUseCase.Callback = object : DeletePhotoUseCase.Callback{

                override fun onPhotoDeleted() {
                    pushPhotoArrayUseCase.execute(userId+"/"+pet?.id+"/pet_photos", uriStrings, pushPhotoArrayCallback)
                }

                override fun onError(errorBundle: ErrorBundle?) {}

            }

            deletePhotoUseCase.execute(userId+"/"+pet?.id+"/pet_photos",  deletePhotoCallback)

        }



    }


}