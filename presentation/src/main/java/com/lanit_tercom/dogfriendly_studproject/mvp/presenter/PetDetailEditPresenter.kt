package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.mapper.PetDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailEditView
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.user.AddPetUseCase

/**
 * Презентер с функцией изменения добавления питомца
 */
class PetDetailEditPresenter(private val addPetUseCase: AddPetUseCase?): BasePresenter() {

    private var userId: String? = null
    private val mapper = PetDtoModelMapper()

    fun initialize(userId: String?) {
        this.userId = userId
    }

    fun setView(view: PetDetailEditView){ this.view = view }

    fun addPet(pet: PetModel?) =
            addPetUseCase?.execute(userId, mapper.map1(pet), addPetCallback)

    private val addPetCallback: AddPetUseCase.Callback = object : AddPetUseCase.Callback {

        override fun onPetAdded() {
            (view as PetDetailEditView).navigateToNext()
        }

        override fun onError(errorBundle: ErrorBundle?) {}

    }
}