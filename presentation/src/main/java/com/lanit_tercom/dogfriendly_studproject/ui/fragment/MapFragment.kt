package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.geofire.UserGeoFire
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.MapPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.MapView
import com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail.PetDetailTestActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.MainNavigationActivity
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.DogAdapter
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
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
import kotlin.math.roundToInt

/**
 * Фрагмент работающий с API googleMaps
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class MapFragment : BaseFragment(), MapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private var userMapPresenter: MapPresenter? = null
    private var map: GoogleMap? = null
    private var mapsApiKey: String? = null
    private var cameraPosition: CameraPosition? = null
    private var requestingLocationUpdates = true
    private var circle: Circle? = null

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

    private var currentLocation: Location? = null

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
        val currentId: String = AuthManagerFirebaseImpl().currentUserId
        var currentUser: UserDto? = null

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
        placesClient = Places.createClient(requireActivity())

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationRequest = createLocationRequest()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    currentLocation = location
                    UserGeoFire().userSetLocation(currentId, location.latitude, location.longitude, object : UserGeoFire.UserLocationCallback {
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
        //button_create_walk.setOnClickListener { v: View? ->  (activity as MapActivity).navigateToWalkCreation(currentId)}
        (activity as MainNavigationActivity).switch_visibility.setOnCheckedChangeListener(this)
        (activity as MainNavigationActivity).button_create_walk.setOnClickListener(this)
        button_radar.visibility = View.GONE
        //(activity as MapActivity).switch_visibility.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

        val dogRecycler = near_list_recycler_view
        dogRecycler.layoutManager = LinearLayoutManager(activity)

        if (isChecked){
            // Prompt the user for permission.
            getLocationPermission()
            if (locationPermissionGranted){

                // Turn on the My Location layer and the related control on the map.
                updateLocationUI()
                button_radar.visibility = View.VISIBLE

                // Get the current location of the device and set the position of the map.
                getDeviceLocation()
                startLocationUpdates()
                fillRecycler()
            }

        }
        else {
            button_radar.visibility = View.GONE
            stopLocationUpdates()
            dogRecycler.adapter = null
            UserGeoFire().userDeleteLocation(currentId)
        }
    }

    private fun fillRecycler(){
        if (locationPermissionGranted){
            val dogRecycler = near_list_recycler_view
            dogRecycler.layoutManager = LinearLayoutManager(activity)
            //Заполнение RecyclerView с собаками поблизости
            val nearUsers = mutableMapOf<String?, List<Double?>>()
            var allUsers: MutableList<UserDto>? = null
            UserGeoFire().userQueryAtLocation(currentId, 5.0, object : UserGeoFire.UserQueryAtLocationCallback {
                override fun onError(exception: java.lang.Exception?) {

                }

                override fun onQueryLoaded(key: String?, latitude: Double?, longitude: Double?) {

                    nearUsers[key] = listOf(latitude, longitude)
                    userMapPresenter?.getUsersDetails(object : GetUsersDetailsUseCase.Callback {
                        override fun onUsersDataLoaded(users: MutableList<UserDto>?) {
                            allUsers = users

                            val names = mutableListOf<String>()
                            val imageIds = mutableListOf<String>()
                            val distances = mutableListOf<Double>()
                            val breeds = mutableListOf<String>()
                            val ages = mutableListOf<Int>()

                            allUsers?.forEach { user ->
                                if (nearUsers.keys.contains(user.id) && user.id != currentId) {
                                    //Крашилось с user.pets must not be null
                                    Log.d("MAP_TEST", user.id)
                                    if (user.pets != null) {
                                        user.pets.forEach { pet ->
                                            names.add(pet.value.name)
                                            if (pet.value.avatar != null)
                                                imageIds.add(pet.value.avatar)
                                            else
                                                imageIds.add("https://firebasestorage.googleapis.com/v0/b/dogfriendlystudproject.appspot.com/o/Uploads%2F-MEYGzlqgcVxSHRV5LQ9%2Favatar?alt=media&token=fc7472ba-bfa2-4885-a6e3-f679a6ccfa78")
                                            breeds.add(pet.value.breed)
                                            ages.add(pet.value.age)
                                            val lat1 = currentLocation?.latitude!!
                                            val long1 = currentLocation?.longitude!!
                                            val lat2 = nearUsers[user.id]?.get(0)!!
                                            val long2 = nearUsers[user.id]?.get(1)!!
                                            var distance = distance(lat1, lat2, long1, long2, 0.0, 0.0).roundToInt()
                                            distances.add(distance.toDouble())
                                        }
                                    }
                                }
                                if (user.id == currentId) currentUser = user
                            }

                            val adapter = DogAdapter(names.toTypedArray(), imageIds.toTypedArray(), distances.toTypedArray(), breeds.toTypedArray(), ages.toTypedArray(), "map")
                            adapter.setListener(object : DogAdapter.Listener {
                                override fun onClick(position: Int) {
                                    startActivity(Intent(activity, PetDetailTestActivity::class.java))
                                }
                            })
                            dogRecycler.adapter = adapter
                        }

                        override fun onError(errorBundle: ErrorBundle?) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            })
        }
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
        if ((activity as MainNavigationActivity).switch_visibility.isChecked && locationPermissionGranted){
            button_radar.visibility = View.VISIBLE

            // Prompt the user for permission.
            getLocationPermission()

            // Turn on the My Location layer and the related control on the map.
            updateLocationUI()

            // Get the current location of the device and set the position of the map.
            getDeviceLocation()
            startLocationUpdates()
            fillRecycler()
        }
        else{
            val dogRecycler = near_list_recycler_view
            button_radar.visibility = View.GONE
            stopLocationUpdates()
            dogRecycler.adapter = null
            UserGeoFire().userDeleteLocation(currentId)
        }
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
        //(activity as MainNavigationActivity).navigateToUserDetail(p0?.title)
        Log.d("MARKER_CLICKED", p0?.title)
        (activity as MainNavigationActivity).navigateToUserDetailObserver(AuthManagerFirebaseImpl().currentUserId, p0?.title)
        //startActivity(Intent(activity, UserDetailObserverFragment::class.java))
        //(activity as MapActivity).navigateToUserDetail(p0?.title)
        return true
    }

    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    override fun onMapReady(googleMap: GoogleMap?) {
        this.map = googleMap
        googleMap?.setOnMarkerClickListener(this)
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
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    LatLng(lastKnownLocation!!.latitude,
                                            lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                            currentLocation = lastKnownLocation
                            UserGeoFire().userSetLocation(currentId, lastKnownLocation!!.latitude, lastKnownLocation!!.longitude, object : UserGeoFire.UserLocationCallback {
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
            requestPermissions(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
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
                    // Get the current location of the device and set the position of the map.
                    getDeviceLocation()
                    startLocationUpdates()
                    fillRecycler()
                } else (activity as MainNavigationActivity).switch_visibility.isChecked = false
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
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    override fun renderUserOnMap(userId: String?, latitude: Double?, longitude: Double?) {
        map?.apply {
            val point = LatLng(latitude!!, longitude!!)
            addMarker(
                    MarkerOptions()
                            .position(point)
                            .anchor(0.5F, 0.5F)
                            .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIconsHalfSize(R.drawable.image_dog_icon)))
                            .title(userId)

            )

        }
    }

    fun resizeMapIconsHalfSize(iconId: Int): Bitmap? {
        val imageBitmap: Bitmap = BitmapFactory.decodeResource(resources, iconId)
        return Bitmap.createScaledBitmap(imageBitmap, imageBitmap.width / 2, imageBitmap.height / 2, false)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.button_radar -> {
                val bottomSheetDialog = BottomSheetDialog(activity as Context, R.style.BottomSheetDialogTheme)
                val bottomSheetView = LayoutInflater
                        .from(activity?.applicationContext)
                        .inflate(R.layout.test_layout_bottom_sheet, bottomSheetContainer)
                bottomSheetView.findViewById<SeekBar>(R.id.seekBar).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        bottomSheetView.findViewById<TextView>(R.id.seekbar_progress).text = "${(seekBar?.progress?.times(50)).toString()} метров"
                        circle?.remove()
                        circle = map?.addCircle(CircleOptions()
                                .center(LatLng(currentLocation!!.latitude, currentLocation!!.longitude))
                                .radius((seekBar!!.progress * 50).toDouble())
                                .fillColor(Color.parseColor("#80808080"))
                                .strokeColor(Color.TRANSPARENT)
                        )

                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        map?.clear()
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        // нахождение пользователей в радиусе
                        userMapPresenter?.initialize(currentId, seekBar?.progress?.times(0.05)!!)
                        bottomSheetDialog.dismiss()
                    }
                })
                bottomSheetDialog.setContentView(bottomSheetView)
                bottomSheetDialog.show()
            }
            R.id.button_create_walk -> {
                (activity as MainNavigationActivity).navigateToWalkCreation(currentId)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userMapPresenter?.setView(this)
        button_radar.setOnClickListener(this)
        // Код ниже устанавливает размер выдвижной панели
        val point = Point()
        activity?.windowManager?.defaultDisplay?.getSize(point)
        val params = bottom_sheet.layoutParams
        val halfScreenHeight = point.y / 2
        params.height = halfScreenHeight
        bottom_sheet.layoutParams = params
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    fun distance(lat1: Double, lat2: Double, lon1: Double,
                 lon2: Double, el1: Double, el2: Double): Double {
        val R = 6371 // Radius of the earth
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        var distance = R * c * 1000 // convert to meters
        val height = el1 - el2
        distance = Math.pow(distance, 2.0) + Math.pow(height, 2.0)
        return Math.sqrt(distance)
    }
}