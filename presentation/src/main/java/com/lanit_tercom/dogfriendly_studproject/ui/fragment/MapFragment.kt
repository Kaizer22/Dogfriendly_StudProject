package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
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
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.test_layout_bottom_sheet.*
import java.util.*

/**
 * Фрагмент работающий с API googleMaps
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class MapFragment : BaseFragment(), MapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var userMapPresenter: MapPresenter? = null
    private var googleMap: GoogleMap? = null
    private var apiKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.packageManager?.getApplicationInfo(context?.packageName, PackageManager.GET_META_DATA)?.apply {
            apiKey = metaData.getString("com.google.android.geo.API_KEY")
        }
    }

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

    @SuppressLint("DefaultLocale")
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

        // Initialize Places.
        Places.initialize(activity?.applicationContext!!, apiKey!!)

        // Create a new Places client instance.
        val placesClient: PlacesClient = Places.createClient(context!!)

        // Use fields to define the data types to return.

        // Use fields to define the data types to return.
        val placeFields: List<Place.Field> = listOf(Place.Field.NAME)

        // Use the builder to create a FindCurrentPlaceRequest.

        // Use the builder to create a FindCurrentPlaceRequest.
        val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.builder(placeFields).build()

        // Call findCurrentPlace and handle the response (first check that the user has granted permission).
        if (ContextCompat.checkSelfPermission(context!!, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            placesClient.findCurrentPlace(request).addOnSuccessListener { response: FindCurrentPlaceResponse ->
                for (placeLikelihood in response.placeLikelihoods) {
                    Log.i(TAG, java.lang.String.format("Place '%s' has likelihood: %f",
                            placeLikelihood.place.name,
                            placeLikelihood.likelihood))
                    textView.append(java.lang.String.format("Place '%s' has likelihood: %f\n",
                            placeLikelihood.place.name,
                            placeLikelihood.likelihood))
                }
            }.addOnFailureListener { exception: Exception ->
                if (exception is ApiException) {
                    val apiException: ApiException = exception as ApiException
                    Log.e(TAG, "Place not found: " + apiException.statusCode)
                }
            }
        } else {
            // A local method to request required permissions;
            // See https://developer.android.com/training/permissions/requesting
            //getLocationPermission()
        }
    }




}