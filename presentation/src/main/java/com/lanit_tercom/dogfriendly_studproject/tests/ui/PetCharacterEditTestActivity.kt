package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R

/**
 * Элемент pet_character_element имеет фиксированную ширину 100dp, что приводит к некорректному отображению на разных экранах
 * Как сделать иначе тут не очень понятно, т.к. при wrap_content элементы будут разными в зависимости от текста ("активная","агрессивная", "добрая" - везде по разному будет)
 */
class PetCharacterEditTestActivity : AppCompatActivity() {
    private lateinit var data: Intent
    private lateinit var backButton: ImageButton
    private lateinit var readyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_character_edit)

        val names = initializeNames()
        val images = initializeImages()

        val characterList = findViewById<RecyclerView>(R.id.character_elements)
        val characterAdapter = CharacterAdapter(images, names)
        val gridLayoutManager = GridLayoutManager(this, 3)
        characterList.layoutManager = gridLayoutManager
        characterList.adapter = characterAdapter

        data = Intent(this, PetPhotoEditTestActivity::class.java)
        data.putExtras(intent)

        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }


        readyButton = findViewById(R.id.ready_button)
        readyButton.setOnClickListener {
            startActivity(data)
        }


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

