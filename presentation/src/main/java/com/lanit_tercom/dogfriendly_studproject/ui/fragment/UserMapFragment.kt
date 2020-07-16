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
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserMapPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserMapView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserMapActivity
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.get.GetUserDetailsUseCase
import com.lanit_tercom.domain.interactor.get.GetUserDetailsUseCaseImpl
import com.lanit_tercom.domain.interactor.get.GetUsersDetailsUseCase
import com.lanit_tercom.domain.interactor.get.GetUsersDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl

/**
 * Фрагмент работающий с API googleMaps
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserMapFragment : BaseFragment(), UserMapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var userMapPresenter: UserMapPresenter? = null
    private var googleMap: GoogleMap? = null

    override fun initializePresenter() {
        val threadExecutor: ThreadExecutor = JobExecutor.getInstance()
        val postExecutionThread: PostExecutionThread = UIThread.getInstance()

        val networkManager: NetworkManager = NetworkManagerImpl(context)
        val userEntityStoreFactory = UserEntityStoreFactory(networkManager, null)
        val userEntityDtoMapper = UserEntityDtoMapper()
        val userRepository: UserRepository = UserRepositoryImpl.getInstance(userEntityStoreFactory,
                userEntityDtoMapper)
        val getUsersDetailsUseCase: GetUsersDetailsUseCase = GetUsersDetailsUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread)

        userMapPresenter = UserMapPresenter(getUsersDetailsUseCase)
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