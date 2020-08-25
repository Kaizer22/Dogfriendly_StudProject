package com.lanit_tercom.dogfriendly_studproject.mvp.view

import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel

interface PetDetailEditView: LoadDataView {

    fun navigateToNext(pet: PetModel)
    fun navigateBack(pet: PetModel)
}