package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserMapFragment

class MainActivity : BaseActivity(), OnMapReadyCallback{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = UserMapFragment()
        addFragment(R.id.activity_main, mapFragment)
        mapFragment.getMapAsync(this)

        //Тут должен запускаться фрагмент с картой, но он еще не готов
        //addFragment(R.id.activity_main, UserDetailFragment())
    }
    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.apply {
            val sydney = LatLng(-33.852, 151.211)
            addMarker(
                    MarkerOptions()
                            .position(sydney)
                            .title("Marker in Sydney")
            )
            // [START_EXCLUDE silent]
            moveCamera(CameraUpdateFactory.newLatLng(sydney))
            // [END_EXCLUDE]
        }
        googleMap?.setOnMarkerClickListener {
            replaceFragment(R.id.activity_main, UserDetailFragment())
            true
        }
    }
}
