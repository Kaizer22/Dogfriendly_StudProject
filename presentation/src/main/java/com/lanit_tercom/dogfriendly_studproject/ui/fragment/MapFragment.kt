package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.geofire.UserGeoFire
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.MapPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.MapView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.MainNavigationActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.MapActivity
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.DogAdapter
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.user.GetUsersDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.GetUsersDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.test_layout_bottom_sheet.*
import java.lang.Exception

/**
 * Фрагмент работающий с API googleMaps
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class MapFragment : BaseFragment(), MapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, CompoundButton.OnCheckedChangeListener {

    private var userMapPresenter: MapPresenter? = null
    private var map: GoogleMap? = null
    private var mapsApiKey: String? = null
    private var cameraPosition: CameraPosition? = null
    private var requestingLocationUpdates = true

    // The entry point to the Places API.
    private lateinit var placesClient: PlacesClient

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var locationRequest: LocationRequest? = null
    private lateinit var locationCallback: LocationCallback


    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private var lastKnownLocation: Location? = null
    private var likelyPlaceNames: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAddresses: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAttributions: Array<List<*>?> = arrayOfNulls(0)
    private var likelyPlaceLatLngs: Array<LatLng?> = arrayOfNulls(0)

    companion object {
        private val TAG = MapFragment::class.java.simpleName
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        // Get mapsApiKey from manifest
        context?.packageManager?.getApplicationInfo(context?.packageName!!, PackageManager.GET_META_DATA)?.apply {
            mapsApiKey = metaData.getString("com.google.android.geo.API_KEY")
        }

        // Construct a PlacesClient
        Places.initialize(activity?.applicationContext!!, mapsApiKey!!)
        placesClient = Places.createClient(activity!!)

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
        locationRequest = createLocationRequest()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    UserGeoFire().userSetLocation("testId", location.latitude, location.longitude, object: UserGeoFire.UserLocationCallback{
                        override fun onError(exception: Exception?) {
                        }

                        override fun onLocationLoaded() {
                        }

                        override fun onLocationSet() {
                        }
                    })
                }
            }
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

        // Build the map.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //(activity as MainNavigationActivity).switch_visibility.setOnCheckedChangeListener(this)
        //(activity as MapActivity).switch_visibility.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) startLocationUpdates()
        else stopLocationUpdates()
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
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
        //(activity as MapActivity).navigateToUserDetail(p0?.title)
        (activity as MainNavigationActivity).navigateToUserDetail(p0?.title)
        return true
    }

    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    override fun onMapReady(googleMap: GoogleMap?) {
        this.map = googleMap
        googleMap?.setOnMarkerClickListener(this)
        userMapPresenter?.initialize()

        // Prompt the user for permission.
        getLocationPermission()

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI()

        // Get the current location of the device and set the position of the map.
        getDeviceLocation()
    }

    private fun createLocationRequest(): LocationRequest? {
        return LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(activity?.applicationContext!!,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper())
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    LatLng(lastKnownLocation!!.latitude,
                                            lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                            UserGeoFire().userSetLocation("testId", lastKnownLocation!!.latitude, lastKnownLocation!!.longitude, object: UserGeoFire.UserLocationCallback{
                                override fun onError(exception: Exception?) {
                                }

                                override fun onLocationLoaded() {
                                }

                                override fun onLocationSet() {
                                }
                            })
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(activity?.applicationContext!!,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    override fun renderUserOnMap(user: UserModel?) {
        map?.apply {
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