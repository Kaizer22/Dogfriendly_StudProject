package com.lanit_tercom.dogfriendly_studproject.tests.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.DogAdapter
import kotlinx.android.synthetic.main.activity_map_settings.*
import kotlinx.android.synthetic.main.fragment_map.*

class MapSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_settings)
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val dogRecycler = map_settings_recycler_view
        val names = arrayOf("Катя", "Лена", "Маша", "Саша")
        val imageIds = arrayOf(R.drawable.image_dog_icon, R.drawable.image_dog_icon, R.drawable.image_dog_icon, R.drawable.image_dog_icon)
        val distances = arrayOf(3, 2, 5, 1)
        val adapter = DogAdapter(names, imageIds, distances, "map_settings")
        dogRecycler.adapter = adapter
        dogRecycler.layoutManager = LinearLayoutManager(this)

    }
}