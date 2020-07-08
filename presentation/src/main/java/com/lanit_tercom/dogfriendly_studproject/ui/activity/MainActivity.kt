package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserMapFragment

class MainActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = UserMapFragment()
        addFragment(R.id.activity_main, mapFragment)

        mapFragment.googleMap?.setOnMarkerClickListener {
            replaceFragment(R.id.activity_main, UserDetailFragment())
            true
        }

    }

}