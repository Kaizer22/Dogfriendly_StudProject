package com.lanit_tercom.dogfriendly_studproject.mvp.model

import android.net.Uri
import java.lang.StringBuilder

data class PetModel(var id: String?,
                    var name: String?,
                    var age: Int?,
                    var breed: String?,
                    var gender: String?,
                    var about: String?,
                    var character: List<String>?,
                    var photos: ArrayList<Uri>?,
                    var avatar: Uri?){
    constructor() : this(null, null, null, null, null, null, null, null, null)
}
