package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailView
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment
import com.lanit_tercom.domain.dto.PetDto
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.photo.DeletePhotoArrayUseCase
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.user.DeletePetUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import java.util.ArrayList

class UserDetailPresenter(private val getUserDetailsUseCase: GetUserDetailsUseCase?,
                          private val deletePetUseCase: DeletePetUseCase,
                          private val deletePhotoUseCase: DeletePhotoUseCase,
                          private val deletePhotoArrayUseCase: DeletePhotoArrayUseCase) : BasePresenter() {

    private var view: UserDetailView? = null
    private var userId: String? = null
    private var userDto: UserDto? = null

    fun initialize(userId: String?) {
        this.userId = userId
        this.loadUserDetails()
    }

    fun setView(view: UserDetailView) {
        this.view = view
    }

    private fun loadUserDetails() =
            getUserDetailsUseCase?.execute(userId, userDetailsCallback)

    //Удаление питомца
    fun deletePet(petId: String?, position: Int) {

        val deletePetCallback: DeletePetUseCase.Callback = object : DeletePetUseCase.Callback {

            override fun onPetDeleted() {}

            override fun onError(errorBundle: ErrorBundle?) {}

        }


        val deletePhotoCallback: DeletePhotoUseCase.Callback = object : DeletePhotoUseCase.Callback {

            override fun onPhotoDeleted() {}

            override fun onError(errorBundle: ErrorBundle?) {}

        }

        val deletePhotoArrayCallback: DeletePhotoArrayUseCase.Callback = object : DeletePhotoArrayUseCase.Callback{
            override fun onPhotoDeleted() {}

            override fun onError(errorBundle: ErrorBundle?) {}

        }

        //Удаляем питомца -> его фото -> его аватар
        deletePetUseCase.execute(userId, petId, deletePetCallback)
        val petDto: PetDto? = userDto?.pets?.get(petId)
        val avatar: String? = petDto?.avatar
        val photos: List<String>? = petDto?.photos

        if (avatar != null) {
            deletePhotoUseCase.execute(avatar, deletePhotoCallback)

        }

        if (photos != null) {

            deletePhotoArrayUseCase.execute(photos as? ArrayList<String>?, deletePhotoArrayCallback)
        }
        

    }

    //Вывод загруженных данных юзера на экран
    private fun showUserDetailsInView(userDto: UserDto?) {
        val userDtoModelMapper = UserDtoModelMapper()
        val userModel = userDtoModelMapper.map2(userDto)
        view?.renderCurrentUser(userModel)


    }

    //Callbacks для загрузки данных пользователя
    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {

        override fun onUserDataLoaded(userDto: UserDto?){
            this@UserDetailPresenter.userDto = userDto
            this@UserDetailPresenter.showUserDetailsInView(userDto)
        }


        override fun onError(errorBundle: ErrorBundle?) {}

    }

    override fun onDestroy() {
        this.view = null
    }
}