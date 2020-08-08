package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.MapPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.MapView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.MapActivity
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.DogAdapter
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.user.GetUsersDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.GetUsersDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.test_layout_bottom_sheet.*

/**
 * Фрагмент работающий с API googleMaps
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class MapFragment : BaseFragment(), MapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var userMapPresenter: MapPresenter? = null
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

        userMapPresenter = MapPresenter(getUsersDetailsUseCase)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
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
        (activity as MapActivity).navigateToUserDetail(p0?.title)
        return true
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        googleMap?.setOnMarkerClickListener(this)
        userMapPresenter?.initialize()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userMapPresenter?.setView(this)
        // ВРЕМЕННЫЙ КОД!!!
        button_radar.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(activity as Context, R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater
                    .from(activity?.applicationContext)
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
        activity?.windowManager?.defaultDisplay?.getSize(point)
        val params = bottom_sheet.layoutParams
        val halfScreenHeight = point.y / 2
        params.height = halfScreenHeight
        bottom_sheet.layoutParams = params

        near_list_recycler_view.layoutManager = LinearLayoutManager(activity)

        val dogRecycler = near_list_recycler_view

        val names = arrayOf("Катя", "Лена", "Маша", "Саша")
        val imageIds = arrayOf(R.drawable.image_dog_icon, R.drawable.image_dog_icon, R.drawable.image_dog_icon, R.drawable.image_dog_icon)
        val distances = arrayOf(3, 2, 5, 1)
        val adapter = DogAdapter(names, imageIds, distances, "map")
        adapter.setListener(object: DogAdapter.Listener{
            override fun onClick(position: Int) {
                Snackbar.make(user_map_test,"Профиль ${names[position]} на расстоянии ${distances[position]} км", Snackbar.LENGTH_SHORT).show()
            }
        })
        dogRecycler.adapter = adapter
        dogRecycler.layoutManager = LinearLayoutManager(activity)
    }

}