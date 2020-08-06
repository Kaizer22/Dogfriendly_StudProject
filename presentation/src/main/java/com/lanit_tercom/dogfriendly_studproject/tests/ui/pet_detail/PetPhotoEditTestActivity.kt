package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R

/**
 * Добалено задаение размера через ItemDecoration.
 * Функционал не тронут.
 */
class PetPhotoEditTestActivity : AppCompatActivity() {
    private lateinit var data: Intent
    private lateinit var backButton: ImageButton
    private lateinit var readyButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_photo_edit)

        val images = initializeImages()

        val photoList = findViewById<RecyclerView>(R.id.photo_elements)
        val photoAdapter = PhotoAdapter(images)
        val gridLayoutManager = GridLayoutManager(this, 3)
        photoList.layoutManager = gridLayoutManager
        photoList.itemAnimator = DefaultItemAnimator()
        photoList.addItemDecoration(SpacesItemDecoration(25))
        photoList.adapter = photoAdapter

        data = Intent(this, PetDetailTestActivity::class.java)
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

    fun initializeImages(): ArrayList<Int> {
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
}

