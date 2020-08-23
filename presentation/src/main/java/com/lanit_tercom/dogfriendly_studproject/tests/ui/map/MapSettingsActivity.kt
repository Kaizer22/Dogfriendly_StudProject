package com.lanit_tercom.dogfriendly_studproject.tests.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.DogAdapter
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.MapFragment
import kotlinx.android.synthetic.main.activity_map_settings.*
import kotlinx.android.synthetic.main.fragment_map.*

class MapSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_settings)
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val user = MapFragment.currentUser
        val pets = user?.pets
        val names = mutableListOf<String>()
        val imageIds = mutableListOf<String>()
        val distances = mutableListOf<Double>()
        val breeds = mutableListOf<String>()
        val ages = mutableListOf<Int>()
        pets?.forEach {
            names.add(it.value.name)
            imageIds.add(it.value.avatar)
        }
        val dogRecycler = map_settings_recycler_view

        val adapter = DogAdapter(names.toTypedArray(), imageIds.toTypedArray(), distances.toTypedArray(), breeds.toTypedArray(), ages.toTypedArray(), "map_settings")
        dogRecycler.adapter = adapter
        dogRecycler.layoutManager = LinearLayoutManager(this)

    }
}