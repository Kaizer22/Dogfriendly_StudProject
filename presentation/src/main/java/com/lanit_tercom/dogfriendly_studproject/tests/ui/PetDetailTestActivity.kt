package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R

/**
 * Прикручено получение характеров из PetCharacterEdit (пока без RecyclerView-selection)
 * Работает криво, но хоть как то.
 */
class PetDetailTestActivity : AppCompatActivity() {
    private lateinit var avatar: ImageView
    private lateinit var btnToPetDetailEdit: ImageButton
    private lateinit var nameTextView: TextView
    private lateinit var infoTextView: TextView
    private var avatarUri: Uri? = null
    private var character: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_detail)

        avatar = findViewById(R.id.pet_avatar)
        nameTextView = findViewById(R.id.name)
        infoTextView = findViewById(R.id.info)

        //Тут берется вся информация, полученная в процессе создания нового питомца (пока кроме фоток)
        if(intent!=null){
            nameTextView.text = intent.getStringExtra("name")
            val age: String? = intent.getStringExtra("age")
            val breed: String? = intent.getStringExtra("breed")
            val info: String? = breed+", "+age+" лет."
            infoTextView.text = info
            avatarUri = Uri.parse(intent?.getStringExtra("avatarUri"))
            if (avatarUri != null)
                avatar.setImageURI(avatarUri)
            else
                avatar.setImageResource(R.drawable.ic_set_avatar_green)
            character = intent.getStringArrayListExtra("character")
        }



        //Все что связано с выводом фоток (пока тут ничего нет по сути)
        val photoImages = initializePhotoImages()
        val photoList = findViewById<RecyclerView>(R.id.photo_list)
        val photoAdapter = PhotoAdapter(photoImages)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        photoList.layoutManager = linearLayoutManager
        photoList.adapter = photoAdapter

        //Все что связано с выводом характеров, работает криво из-за того что элемент теперь имеет ширину match_parent
        if(character!=null){
            val names = getCharacterNames(character!!)
            val characterImages = getCharacterImages(character!!)
            val characterList = findViewById<RecyclerView>(R.id.character_list)
            val characterAdapter = CharacterAdapter(characterImages, names, null)
            val linearLayoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            characterList.layoutManager = linearLayoutManager2
            characterList.itemAnimator = DefaultItemAnimator()
            characterList.addItemDecoration(SpacesItemDecoration(0))
            characterList.adapter = characterAdapter

        }

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



    override fun onBackPressed() {
        val toUserDetail: Intent = Intent(this, UserDetailTestActivity::class.java)
        startActivity(toUserDetail)
    }


    //Выбранные элементы с PetCharacterEditActivity прилетают в виде номеров из списка (ну пока так)
    //Для того чтобы вытащить необходимую строку и картинку нужны эти два метода.
    fun getCharacterNames(characters: List<String>): List<String>{
        val names: ArrayList<String> = ArrayList()
        for(character in characters){
            when(character){
                "1" -> names.add("Активная")
                "2" -> names.add("Добрая")
                "3" -> names.add("Гордая")
                "4" -> names.add("Трусливая")
                "5" -> names.add("Агрессивная")
                "6" -> names.add("Гроза белок")
                "7" -> names.add("Сорванец")
                "8" -> names.add("Веселая")
                "9" -> names.add("Боевая")
                "10" -> names.add("Спортивная")
                "11" -> names.add("Неусидчивая")
                "12" -> names.add("Застенчивая")
            }
        }
        return names
    }

    fun getCharacterImages(characters: List<String>): List<Int>{
        val images: ArrayList<Int> = ArrayList()
        for(character in characters){
            when(character){
                "1" -> images.add(R.drawable.ic_round_circle)
                "2" -> images.add(R.drawable.ic_round_circle)
                "3" -> images.add(R.drawable.ic_round_circle)
                "4" -> images.add(R.drawable.ic_round_circle)
                "5" -> images.add(R.drawable.ic_round_circle)
                "6" -> images.add(R.drawable.ic_round_circle)
                "7" -> images.add(R.drawable.ic_round_circle)
                "8" -> images.add(R.drawable.ic_round_circle)
                "9" -> images.add(R.drawable.ic_round_circle)
                "10" -> images.add(R.drawable.ic_round_circle)
                "11" -> images.add(R.drawable.ic_round_circle)
                "12" -> images.add(R.drawable.ic_round_circle)
            }
        }
        return images
    }

}