package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R
import kotlinx.android.synthetic.main.pet_character_element.view.*

class PetCharacterEditTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_character_edit)

        //Наверное стоит объеденить через Map
        val names = initializeNames()
        val images = initializeImages()

        val characterList = findViewById<RecyclerView>(R.id.character_elements)
        val characterAdapter = CharacterAdapter(images, names)
        val gridLayoutManager = GridLayoutManager(this, 3)
        characterList.layoutManager = gridLayoutManager
        characterList.adapter = characterAdapter

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

    fun initializeImages(): ArrayList<Int> {
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

