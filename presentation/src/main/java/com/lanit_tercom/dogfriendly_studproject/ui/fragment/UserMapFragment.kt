package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UseCaseTemp
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserMapPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserMapView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserMapActivity

/**
 * Фрагмент работающий с API googleMaps
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserMapFragment : BaseFragment(), UserMapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var userMapPresenter: UserMapPresenter? = null
    private var googleMap: GoogleMap? = null

    override fun initializePresenter() {
        userMapPresenter = UserMapPresenter( UseCaseTemp())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userMapPresenter?.setView(this)
    }

    override fun onPause() {
        super.onPause()
        userMapPresenter?.onPause()
    }

    override fun onResume() {
        super.onResume()
        userMapPresenter?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        userMapPresenter?.onDestroy()
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        TODO("Not yet implemented")
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        (activity as UserMapActivity).navigateToUserDetail(p0?.title)
        return true
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        val users: MutableList<UserModel>? = userMapPresenter?.loadUsers()
        userMapPresenter?.renderMap(users)
        googleMap?.setOnMarkerClickListener(this)
    }

    override fun renderUserOnMap(user: UserModel?) {
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