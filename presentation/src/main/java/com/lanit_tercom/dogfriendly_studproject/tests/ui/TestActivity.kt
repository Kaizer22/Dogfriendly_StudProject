package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap

import com.lanit_tercom.dogfriendly_studproject.R
import kotlinx.android.synthetic.main.user_map_test.*

class TestActivity : AppCompatActivity() {

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_map_test)
        button_radar.setOnClickListener {

            if (search_container.visibility == View.VISIBLE) return@setOnClickListener

            seekbar_container.visibility = View.VISIBLE
            textView_radar.visibility = View.VISIBLE
            seekBar.visibility = View.VISIBLE

            seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar?.visibility = View.GONE
                    seekbar_container.visibility = View.GONE
                    textView_radar.visibility = View.GONE
                }
            })
        }
        button_location.setOnClickListener {
            if (seekbar_container.visibility == View.VISIBLE) return@setOnClickListener
            search_container.visibility = View.VISIBLE
            button_search.visibility = View.VISIBLE

            button_search.setOnClickListener {
                search_container.visibility = View.GONE
                button_search.visibility = View.GONE
            }
        }
    }

}