package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.ShareActionProvider
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.tests.ui.user_detail.UserDetailTestActivity
import kotlinx.android.synthetic.main.pet_detail_new.*

/**
 * Пока это не нужно, раз Саша это делает
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
        setContentView(R.layout.pet_detail_new)
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.title = null

//        avatar = findViewById(R.id.pet_avatar)
//        nameTextView = findViewById(R.id.name)
//        infoTextView = findViewById(R.id.info)
//
//        //Тут берется вся информация, полученная в процессе создания нового питомца (пока кроме фоток)
//        if(intent!=null){
//            nameTextView.text = intent.getStringExtra("name")
//            val age: String? = intent.getStringExtra("age")
//            val breed: String? = intent.getStringExtra("breed")
//            val info: String? = breed+", "+age+" лет."
//            infoTextView.text = info
//            avatarUri = Uri.parse(intent?.getStringExtra("avatarUri"))
//            if (avatarUri != null)
//                avatar.setImageURI(avatarUri)
//            else
//                avatar.setImageResource(R.drawable.ic_set_avatar_green)
//            character = intent.getStringArrayListExtra("character")
//        }
//
//
//
//        //Все что связано с выводом фоток (пока тут ничего нет по сути)
//        val photoImages = initializePhotoImages()
//        val photoList = findViewById<RecyclerView>(R.id.photo_list)
//        val photoAdapter = PhotoAdapter(photoImages)
//        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        photoList.layoutManager = linearLayoutManager
//        photoList.adapter = photoAdapter
//
//        //Все что связано с выводом характеров, работает криво из-за того что элемент теперь имеет ширину match_parent
//        if(character!=null){
//            val elements = Character.generateCharacters()
//            val names = getCharacterNames(character!!)
//            val characterImages = getCharacterImages(character!!)
//            val characterList = findViewById<RecyclerView>(R.id.character_list)
//            val characterAdapter = CharacterAdapter()
//            val linearLayoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            characterList.layoutManager = linearLayoutManager2
//            characterList.itemAnimator = DefaultItemAnimator()
//            characterList.addItemDecoration(SpacesItemDecoration(0))
//            characterList.adapter = characterAdapter
//
//        }

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




}