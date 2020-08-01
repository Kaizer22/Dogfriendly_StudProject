package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.DogAdapter
import kotlinx.android.synthetic.main.test_layout_bottom_sheet.*
import kotlinx.android.synthetic.main.user_map_test.*


class TestActivity : AppCompatActivity() {

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_map_test)
        var flag: Boolean = true
        button_visibility.setOnClickListener {
            if (!flag){
                button_visibility.setImageDrawable(getDrawable(R.drawable.ic_visibility_inactive))
                flag = true
            } else if (flag){
                button_visibility.setImageDrawable(getDrawable(R.drawable.ic_visibility_active))
                flag = false
            }
        }
        button_radar.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater
                    .from(applicationContext)
                    .inflate(R.layout.test_layout_bottom_sheet, bottomSheetContainer)
            bottomSheetView.findViewById<SeekBar>(R.id.seekBar).setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    bottomSheetView.findViewById<TextView>(R.id.seekbar_progress).text = (seekBar?.progress?.div(10)).toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    bottomSheetDialog.dismiss()
                }
            })
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
        button_location.setOnClickListener {
            button_search.visibility = View.VISIBLE
            button_search.setOnClickListener { button_search.visibility = View.GONE }
        }

        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        val params = bottom_sheet.layoutParams
        val halfScreenHeight = point.y / 2
        params.height = halfScreenHeight
        bottom_sheet.layoutParams = params

        near_list_recycler_view.layoutManager = LinearLayoutManager(this)

        val dogRecycler = near_list_recycler_view

        val names = arrayOf("Катя", "Лена", "Маша", "Саша")
        val imageIds = arrayOf(R.drawable.image_dog_icon, R.drawable.image_dog_icon, R.drawable.image_dog_icon, R.drawable.image_dog_icon)
        val distances = arrayOf(3, 2, 5, 1)

        dogRecycler.adapter = DogAdapter(names, imageIds, distances)
        dogRecycler.layoutManager = LinearLayoutManager(this)






    }

}