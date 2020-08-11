package com.lanit_tercom.dogfriendly_studproject.mvp.model

import android.net.Uri

data class PetModel(val id: String,
                    val name: String,
                    val age: Int,
                    val breed: String,
                    val gender: String,
                    val about: String,
                    val character: List<String>,
                    val photos: List<Uri>,
                    val avatar: String)
