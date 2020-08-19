package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.net.Uri
import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.mapper.PetDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailEditView
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.PushPhotoArrayUseCase
import com.lanit_tercom.domain.interactor.user.AddPetUseCase
import java.util.ArrayList


class PetPhotoPresenter(private val addPetUseCase: AddPetUseCase?, private val pushPhotoArrayUseCase: PushPhotoArrayUseCase) : BasePresenter() {

    private var view: PetDetailEditView? = null
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

        override fun onError(errorBundle: ErrorBundle?) {
            val s = errorBundle?.exception?.message
            Log.i("TEST_ACTIVITY", s)
        }

    }

    /**
     * Вызываем addPet
     * addPet отправляет фото и получает ссылку
     * После успешного получения ссылки в базу добавляется уже вся модель вместе с полученной ссылкой
     */
    fun addPet(pet: PetModel?, uriStrings: ArrayList<String>) {

        val pushPhotoArrayCallback: PushPhotoArrayUseCase.Callback = object : PushPhotoArrayUseCase.Callback {

            override fun onPhotoArrayPushed(downloadUris: ArrayList<String>?) {
                val photos = ArrayList<Uri>()
                for(downloadUri in downloadUris!!) photos.add(Uri.parse(downloadUri))
                pet?.photos = photos
                addPetUseCase?.execute(userId, mapper.map1(pet), addPetCallback)
            }

            override fun onError(errorBundle: ErrorBundle?) {
                val s = errorBundle?.exception?.message
                Log.i("TEST_ACTIVITY", s)
            }

        }

        pushPhotoArrayUseCase.execute("$userId/pet_photos", uriStrings, pushPhotoArrayCallback)
    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }


}