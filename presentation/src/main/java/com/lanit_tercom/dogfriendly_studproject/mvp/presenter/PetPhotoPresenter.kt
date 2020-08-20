package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.net.Uri
import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.mapper.PetDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailEditView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.PetDetailEditFragment
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.PetPhotoFragment
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoArrayUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase
import com.lanit_tercom.domain.interactor.user.AddPetUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import java.util.ArrayList


class PetPhotoPresenter(private val getUserDetailsUseCase: GetUserDetailsUseCase,
                        private val addPetUseCase: AddPetUseCase,
                        private val deletePhotoUseCase: DeletePhotoUseCase,
                        private val pushPhotoUseCase: PushPhotoUseCase,
                        private val pushPhotoArrayUseCase: PushPhotoArrayUseCase) : BasePresenter() {

    private var userId: String? = null
    private var petId: String? = null
    private val mapper = PetDtoModelMapper()

    fun initialize(userId: String?, petId: String?) {
        this.userId = userId
        this.petId = petId
        loadPetDetails()
    }

    fun setView(view: PetDetailEditView) {
        this.view = view
    }

    //Запрашивает у бд модельку юзера
    fun loadPetDetails() =
            getUserDetailsUseCase.execute(userId, userDetailsCallback)

    //Ищет есть ли в массиве питомцев питомец с определенным id
    private fun findPet(id: String?, pets: ArrayList<PetModel>): PetModel?{
        for(pet in pets)
            if(pet.id == id) return pet
        return null
    }

    //Если питомец с таким id уже есть - грузим его фото с firebase
    private fun showPetDetailsInView(userDto: UserDto?) {
        val userDtoModelMapper = UserDtoModelMapper()
        val userModel = userDtoModelMapper.map2(userDto)
        if(userModel.pets!=null){
            val model = findPet(petId, userModel.pets as ArrayList)
            (view as PetPhotoFragment).initializeView(model)
        }


    }

    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {

        override fun onUserDataLoaded(userDto: UserDto?) = showPetDetailsInView(userDto)

        override fun onError(errorBundle: ErrorBundle?) {}

    }

    /**
     * тут твориться полнейший ад, все беды этого кода рождаются тут
     * Если вкратце - механизм такой
     * 1)проверяем является ли аватар null - если да, сразу переходим к отправкн фото, если нет - отправляем автатар
     * 2)проверяем есть ли фото уже в бд (внутри модельки питомца) и есть ли выбранные новые - в зависимости от этого добавляем/удаляем/пропускаем
     * 3)добавляем новую/обновленную модель
     */
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
            deletePhotoUseCase.execute(userId+"/"+pet.id+"/pet_photos", deletePhotoArrayCallback)
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


}