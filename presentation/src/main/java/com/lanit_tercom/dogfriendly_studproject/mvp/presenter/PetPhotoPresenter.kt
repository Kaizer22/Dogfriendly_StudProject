package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.net.Uri
import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.mapper.PetDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailEditView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.PetPhotoFragment
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoArrayUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase
import com.lanit_tercom.domain.interactor.user.AddPetUseCase
import java.util.ArrayList


class PetPhotoPresenter(private val addPetUseCase: AddPetUseCase,
                        private val deletePhotoUseCase: DeletePhotoUseCase,
                        private val pushPhotoUseCase: PushPhotoUseCase,
                        private val pushPhotoArrayUseCase: PushPhotoArrayUseCase) : BasePresenter() {

    private var view: PetDetailEditView? = null
    private var userId: String? = null
    private var petId: String? = null
    private val mapper = PetDtoModelMapper()

    fun initialize(userId: String?, petId: String?) {
        this.userId = userId
        this.petId = petId
    }

    fun setView(view: PetDetailEditView) {
        this.view = view
    }

    //Вроде работает нормально (добавляет: аватар -> фото -> сама модель юзера)
    fun addPet(pet: PetModel ,uriStrings: ArrayList<String>) {
        //3
        val addPetCallback: AddPetUseCase.Callback = object : AddPetUseCase.Callback {

            override fun onPetAdded() {
                (view as PetPhotoFragment).navigateToNext(PetModel())
                Log.i("PHOTO_PRESENTER", "ADD_PET_SUCCESS")
            }

            override fun onError(errorBundle: ErrorBundle?) {
                Log.i("PHOTO_PRESENTER", "ADD_PET_FAILURE")
            }

        }

        //2
        val pushPhotoArrayCallback: PushPhotoArrayUseCase.Callback = object : PushPhotoArrayUseCase.Callback {

            override fun onPhotoArrayPushed(downloadUris: ArrayList<String>?) {
                Log.i("PHOTO_PRESENTER", "PUSH_PHOTO_ARRAY_SUCCESS")

                if (downloadUris != null) {
                    val photoUriList = ArrayList<Uri>()
                    for(photo in downloadUris)
                        photoUriList.add(Uri.parse(photo))
                    pet.photos = photoUriList
                }
                addPetUseCase.execute(userId, mapper.map1(pet), addPetCallback)
            }

            override fun onError(errorBundle: ErrorBundle?) {
                Log.i("PHOTO_PRESENTER", "PUSH_PHOTO_ARRAY_FAILURE")
            }

        }

        val deletePhotoArrayCallback: DeletePhotoUseCase.Callback = object : DeletePhotoUseCase.Callback{

            override fun onPhotoDeleted() {
                Log.i("PHOTO_PRESENTER", "ARRAY_PHOTO_DELETE_SUCCESS")
            }

            override fun onError(errorBundle: ErrorBundle?) {
                Log.i("PHOTO_PRESENTER", "ARRAY_PHOTO_DELETE_FAILURE")
            }

        }

        fun deletePhotoArray(pet:PetModel){
            if(pet.photos != null)
                for(photo in pet.photos!!)
                    deletePhotoUseCase.execute(photo.toString(), deletePhotoArrayCallback)
        }

        fun pushPhotoArray(){
            if(uriStrings.isEmpty()){
                if(pet.photos == null){
                    addPetUseCase.execute(userId, mapper.map1(pet), addPetCallback)
                } else{
                    deletePhotoArray(pet)
                    addPetUseCase.execute(userId, mapper.map1(pet), addPetCallback)
                }
            } else {
                if(pet.photos == null){
                    pushPhotoArrayUseCase.execute(userId+"/"+pet.id+"/pet_photos", uriStrings, pushPhotoArrayCallback)
                } else{
                    deletePhotoArray(pet)
                    pushPhotoArrayUseCase.execute(userId+"/"+pet.id+"/pet_photos", uriStrings, pushPhotoArrayCallback)

                }
            }

        }
        //1
        val pushPhotoCallback: PushPhotoUseCase.Callback = object : PushPhotoUseCase.Callback {

            override fun onPhotoPushed(downloadUri: String?) {
                Log.i("PHOTO_PRESENTER", "PUSH_AVATAR_FINISHED")
                pet.avatar = Uri.parse(downloadUri)
                pushPhotoArray()
            }

            override fun onError(errorBundle: ErrorBundle) {
                Log.i("PHOTO_PRESENTER", "PUSH_AVATAR_FAILURE")
            }

        }

        if(pet.avatar == null){
            pushPhotoArray()
        } else {
            pushPhotoUseCase.execute("$userId/avatar", pet.avatar.toString(), pushPhotoCallback)
            Log.i("PHOTO_PRESENTER", "PUSH_AVATAR_START")

        }

    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }


}