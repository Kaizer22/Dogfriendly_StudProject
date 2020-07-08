package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.MainActivity


class UserMapFragment : SupportMapFragment(), UserDetailsView, OnMapReadyCallback {

    var googleMap: GoogleMap? = null
    private val presenter = UserDetailPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getMapAsync(this)



        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        Log.i("mapState", googleMap.toString())

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        presenter.fillListOfActiveUsers()
        presenter.renderMap()

        googleMap?.setOnMarkerClickListener {
            //Да да, дебильный способ, но я пока не придумал лучше
            if (activity is MainActivity)
                (activity as MainActivity).replace(UserDetailFragment())

            true
        }
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