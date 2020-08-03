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

class PetPhotoEditTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_photo_edit)

        val images = initializeImages()

        val photoList = findViewById<RecyclerView>(R.id.photo_elements)
        val photoAdapter = PhotoAdapter(images)
        val gridLayoutManager = GridLayoutManager(this, 3)
        photoList.layoutManager = gridLayoutManager
        photoList.adapter = photoAdapter

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

