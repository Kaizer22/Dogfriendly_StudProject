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
 * Установка размеров через ItemDecoration работает кривовато
 */
class PetCharacterEditTestActivity : AppCompatActivity() , CharacterAdapter.OnCharacterListener{
    private lateinit var data: Intent
    private lateinit var backButton: ImageButton
    private lateinit var readyButton: Button
    private lateinit var elements: List<Character>
    private val pickedElements: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_character_edit)

        val characterList = findViewById<RecyclerView>(R.id.character_elements)
        elements =  Character.generateCharacters()


        val characterAdapter = CharacterAdapter(elements, this)
        val gridLayoutManager = GridLayoutManager(this, 3)
        characterList.itemAnimator = DefaultItemAnimator()

//        characterList.addItemDecoration(SpacesItemDecoration(10))
        characterList.addItemDecoration(GridSpacingItemDecorator(3, resources.getDimensionPixelSize(R.dimen.recycler_view_item_width), true, 12))

        characterList.layoutManager = gridLayoutManager
        characterList.adapter = characterAdapter

        data = Intent(this, PetPhotoActivity::class.java)
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

    override fun onCharacterClick(position: Int) {
        val element: Character = elements[position]
        if(pickedElements.contains(element.getName()))
            pickedElements.remove(element.getName())
        else
            pickedElements.add(element.getName())
        Toast.makeText(this, pickedElements.size.toString(), Toast.LENGTH_SHORT).show()
    }


}

