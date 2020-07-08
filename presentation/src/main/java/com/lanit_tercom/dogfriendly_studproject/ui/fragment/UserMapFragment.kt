package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView



class UserMapFragment : SupportMapFragment(), UserDetailsView, OnMapReadyCallback {

    var googleMap: GoogleMap? = null
    private val presenter = UserDetailPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getMapAsync(this)
        presenter.fillListOfActiveUsers()


        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        Log.i("mapState", googleMap.toString())
        presenter.renderMap()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
    }


    override fun renderCurrentUser(user: UserModel) {
        googleMap?.apply {
            val point = LatLng(user.point.x, user.point.y)
            addMarker(
                    MarkerOptions()
                            .position(point)
                            .title("id: ${user.id} name: ${user.name}")
            )
            //moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }

}