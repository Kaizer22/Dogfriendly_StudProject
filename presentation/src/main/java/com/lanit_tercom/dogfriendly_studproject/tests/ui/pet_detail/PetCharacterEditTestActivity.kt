package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R


/**
 * С выделением пока не разбирался
 * Добавлено задание размера элементам через ItemDecoration
 */
class PetCharacterEditTestActivity : AppCompatActivity() {
    private lateinit var data: Intent
    private lateinit var backButton: ImageButton
    private lateinit var readyButton: Button
    private lateinit var onClickInterface: OnClickInterface
    private val pickedElements: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_character_edit)

        val characterList = findViewById<RecyclerView>(R.id.character_elements)
        val names = initializeNames()
        val images = initializeImages()

        onClickInterface = object : OnClickInterface {
            override fun setClick(i: Int) {
                pickedElements.add(i.toString())

                Toast.makeText(this@PetCharacterEditTestActivity, i.toString(), Toast.LENGTH_SHORT).show()

            }
        }


        val characterAdapter = CharacterAdapter(images, names, onClickInterface)
        val gridLayoutManager = GridLayoutManager(this, 3)
        characterList.itemAnimator = DefaultItemAnimator()
        characterList.addItemDecoration(SpacesItemDecoration(10))
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
            data.putStringArrayListExtra("character", pickedElements)
            startActivity(data)
        }


    }

    fun initializeNames(): ArrayList<String> {
        val names = ArrayList<String>()
        names.add("Активная")
        names.add("Добрая")
        names.add("Гордая")
        names.add("Трусливая")
        names.add("Агрессивная")
        names.add("Гроза белок")
        names.add("Сорванец")
        names.add("Веселая")
        names.add("Боевая")
        names.add("Спортивная")
        names.add("Неусидчивая")
        names.add("Застенчивая")
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

