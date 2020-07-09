package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
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

/**
 * Фрагмент работающий с API googleMaps
 * @author prostak.sasha111@mail.ru
 */
class UserMapFragment : BaseFragment(), UserDetailsView, OnMapReadyCallback {

    var googleMap: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        userDetailPresenter?.fillListOfActiveUsers()
        userDetailPresenter?.renderMap()


        googleMap?.setOnMarkerClickListener {marker ->
            val userDetailFragment = UserDetailFragment()
            val user = userDetailPresenter?.listOfActiveUsers?.find { it.id == marker.title.toInt() }
            userDetailFragment.attachUser(user)
            (activity as BaseActivity).replaceFragment(R.id.ft_container, userDetailFragment)

            true
        }
    }

    override fun renderCurrentUser(user: UserModel?) {
        googleMap?.apply {
            val point = LatLng(user?.point?.x ?: 0.0, user?.point?.y ?: 0.0)
            addMarker(
                    MarkerOptions()
                            .position(point)
                            .title("${user?.id}")
            )
        }
    }

}