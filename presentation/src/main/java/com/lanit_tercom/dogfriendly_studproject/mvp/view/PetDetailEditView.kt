package com.lanit_tercom.dogfriendly_studproject.mvp.view

import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel

interface PetDetailEditView: LoadDataView {
    var pet: PetModel

    fun navigateToNext(pet: PetModel)
}