package com.lanit_tercom.dogfriendly_studproject.tests.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R

class PetDetailTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_detail)

        val photoImages = initializePhotoImages()
        val names = initializeNames()
        val characterImages = initializeCharacterImages()

        val photoList = findViewById<RecyclerView>(R.id.photo_list)
        val photoAdapter = PhotoAdapter(photoImages)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        photoList.layoutManager = linearLayoutManager
        photoList.adapter = photoAdapter


        val characterList = findViewById<RecyclerView>(R.id.character_list)
        val characterAdapter = CharacterAdapter(characterImages, names)
        val linearLayoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        characterList.layoutManager = linearLayoutManager2
        characterList.adapter = characterAdapter


    }

    fun initializePhotoImages(): ArrayList<Int> {
        val images = ArrayList<Int>()
        images.add(R.drawable.ic_button_add_photo)
        images.add(R.drawable.ic_button_add_photo)
        images.add(R.drawable.ic_button_add_photo)
        images.add(R.drawable.ic_button_add_photo)
        images.add(R.drawable.ic_button_add_photo)
        images.add(R.drawable.ic_button_add_photo)
        images.add(R.drawable.ic_button_add_photo)
        images.add(R.drawable.ic_button_add_photo)
        images.add(R.drawable.ic_button_add_photo)
        return images
    }

    fun initializeNames(): ArrayList<String> {
        val names = ArrayList<String>()
        names.add("Активная")
        names.add("Добрая")
        names.add("Гордая")
        names.add("Трусливая")

        names.add("Активная")
        names.add("Добрая")
        names.add("Гордая")
        names.add("Трусливая")

        names.add("Активная")
        names.add("Добрая")
        names.add("Гордая")
        names.add("Трусливая")


        return names
    }

    fun initializeCharacterImages(): ArrayList<Int> {
        val images = ArrayList<Int>()
        images.add(R.drawable.ic_round_circle)
        images.add(R.drawable.ic_round_circle)
        images.add(R.drawable.ic_round_circle)
        images.add(R.drawable.ic_round_circle)

        images.add(R.drawable.ic_round_circle)
        images.add(R.drawable.ic_round_circle)
        images.add(R.drawable.ic_round_circle)
        images.add(R.drawable.ic_round_circle)

        images.add(R.drawable.ic_round_circle)
        images.add(R.drawable.ic_round_circle)
        images.add(R.drawable.ic_round_circle)
        images.add(R.drawable.ic_round_circle)
        return images
    }

}