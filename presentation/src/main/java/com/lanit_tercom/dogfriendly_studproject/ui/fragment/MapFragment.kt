package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.geofire.UserGeoFire
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mapper.PetDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.MapPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.MapView
import com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail.PetDetailTestActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.MainNavigationActivity
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.DogAdapter
import com.lanit_tercom.domain.dto.PetDto
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
    // Дефолтный аватар собаки
    private val defaultDogAvatar = "https://firebasestorage.googleapis.com/v0/b/dogfriendlystudproject.appspot.com/o/Uploads%2Fistockphoto-912015170-612x612.png?alt=media&token=a3844312-97ce-48e4-a549-3d204771c14f"

    // The entry point to the Places API.
    private lateinit var placesClient: PlacesClient

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationRequest: LocationRequest? = null
    private lateinit var locationCallback: LocationCallback

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var currentLocation: Location? = null

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private var lastKnownLocation: Location? = null

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

        //TODO Перенести логику в презентер (если возможно)
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
                            if (activity != null && (activity as MainNavigationActivity).switch_visibility.isChecked)
                                fillRecycler()
                        }
                    })
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Build the map.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userMapPresenter?.setView(this)
        // Установка listener'a для кнопок поиска и текущей локации
        button_radar.setOnClickListener(this)
        button_location.setOnClickListener(this)

        // Код ниже устанавливает размер выдвижной панели
        val point = Point()
        activity?.windowManager?.defaultDisplay?.getSize(point)
        val params = bottom_sheet.layoutParams
        val halfScreenHeight = point.y / 2
        params.height = halfScreenHeight
        bottom_sheet.layoutParams = params
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainNavigationActivity).switch_visibility.setOnCheckedChangeListener(this)
        (activity as MainNavigationActivity).button_create_walk.setOnClickListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val dogRecycler = near_list_recycler_view
        if (isChecked){
            // Prompt the user for permission.
            if (!locationPermissionGranted()) {
                getLocationPermission()
            }
            if (locationPermissionGranted()){
                // Get the current location of the device and set the position of the map.
                getDeviceLocation()
                startLocationUpdates()
            }
        }
        else {
            stopLocationUpdates()
            dogRecycler.adapter = null
            dogRecycler.layoutManager = LinearLayoutManager(activity)
            UserGeoFire().userDeleteLocation(currentId)
        }
    }

    private fun fillRecycler(){
        //TODO Перенести логику в презентер (если возможно)
        if (locationPermissionGranted() && ((activity as MainNavigationActivity).switch_visibility).isChecked){
            val dogRecycler = near_list_recycler_view
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
                            val userIds = mutableListOf<String>()
                            val petDtos = mutableListOf<PetDto>()
                            val names = mutableListOf<String>()
                            val imageIds = mutableListOf<String>()
                            val distances = mutableListOf<Int?>()
                            val breeds = mutableListOf<String>()
                            val ages = mutableListOf<Int>()

                            allUsers?.forEach { user ->
                                if (nearUsers.keys.contains(user.id) && user.id != currentId) {
                                    if (user.pets != null) {
                                        user.pets.forEach { pet ->
                                            userIds.add(user.id)
                                            petDtos.add(pet.value)
                                            names.add(pet.value.name)
                                            if (pet.value.avatar != null)
                                                imageIds.add(pet.value.avatar)
                                            else
                                                imageIds.add(defaultDogAvatar)
                                            breeds.add(pet.value.breed)
                                            ages.add(pet.value.age)
                                            val lat1 = currentLocation?.latitude!!
                                            val long1 = currentLocation?.longitude!!
                                            val lat2 = nearUsers[user.id]?.get(0)!!
                                            val long2 = nearUsers[user.id]?.get(1)!!
                                            var distance = userMapPresenter?.distance(lat1, lat2, long1, long2, 0.0, 0.0)?.roundToInt()
                                            distances.add(distance)
                                        }
                                    }
                                }
                                if (user.id == currentId) currentUser = user
                            }

                            val adapter = DogAdapter(userIds.toTypedArray(), petDtos.toTypedArray(), names.toTypedArray(), imageIds.toTypedArray(), distances.toTypedArray(), breeds.toTypedArray(), ages.toTypedArray(), "map")
                            adapter.setListener(object : DogAdapter.Listener {
                                override fun onClick(position: Int) {
                                    //Вот тут я хочу получить UserId хозяйна питомца
                                    val userId = userIds[position]
                                    val petDto = petDtos[position]
                                    val mapper =  PetDtoModelMapper()
                                    (activity as MainNavigationActivity).startPetDetailObserver(userId, mapper.map2(petDto))
                                }
                            })
                            if (dogRecycler != null){
                                dogRecycler.adapter = adapter
                                dogRecycler.layoutManager = LinearLayoutManager(activity)
                            }
                        }

                        override fun onError(errorBundle: ErrorBundle?) {
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
        if ((activity as MainNavigationActivity).switch_visibility.isChecked && locationPermissionGranted()){
            startLocationUpdates()
        }
        userMapPresenter?.onResume()
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        val mapper =  PetDtoModelMapper()
        val userId = p0?.title
        (activity as MainNavigationActivity).startPetDetailObserver(userId, mapper.map2(p0?.tag as PetDto))
        return true
    }

    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    override fun onMapReady(googleMap: GoogleMap?) {
        this.map = googleMap
        googleMap?.setOnMarkerClickListener(this)

        // Получение permission при запуске
        if (!locationPermissionGranted()) getLocationPermission()
    }

    private fun createLocationRequest(): LocationRequest? {
        return LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun startLocationUpdates() {
        //не получается заменить на проверку методом
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
            if (locationPermissionGranted()) {
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
                            //TODO Перенести логику в презентер (если возможно)
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
     * Checking permissions
     */
    private fun locationPermissionGranted(): Boolean = (ContextCompat.checkSelfPermission(activity?.applicationContext!!,
            Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED)

    /**
     * Prompts the user for permission to use the device location.
     */
    private fun getLocationPermission() {
        /*
         * The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        requestPermissions(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
    }



    /**
     * Handles the result of the request for location permissions.
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                (activity as MainNavigationActivity).switch_visibility.isChecked = grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED && ((activity as MainNavigationActivity).switch_visibility.isChecked)
            }
        }
    }


    override fun renderUserOnMap(userId: String?, pet: PetDto?, latitude: Double?, longitude: Double?) {
        map?.apply {
            val point = LatLng(latitude!!, longitude!!)
            val imageView = ImageView(requireActivity())
            Glide.with(requireActivity())
                    .asBitmap()
                    .load(pet?.avatar ?: defaultDogAvatar)
                    .circleCrop()
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            val marker = addMarker(
                                    MarkerOptions()
                                            .position(point)
                                            .anchor(0.5F, 0.5F)
                                            .icon(BitmapDescriptorFactory.fromBitmap(userMapPresenter?.resizeMapIcons(resource, 5)))
                                            .title(userId)
                            )
                            marker.tag = pet
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {
                            // this is called when imageView is cleared on lifecycle call or for
                            // some other reason.
                            // if you are referencing the bitmap somewhere else too other than this imageView
                            // clear it here as you can no longer have the bitmap
                        }
                    })
        }
    }



    override fun onClick(v: View?) {
        when (v?.id){
            // Камера на текущее положение
            R.id.button_location -> {
                if (!locationPermissionGranted()) getLocationPermission()
                else {
                    getDeviceLocation()
                    userMapPresenter?.initialize(currentId,0.0)
                }
            }
            //Открытие окна с seekBar для поиска поблизости
            R.id.button_radar -> {
                //Получение permission
                if (!locationPermissionGranted())
                    getLocationPermission()
                //Permission получен, режим поиска отключен
                if (!(activity as MainNavigationActivity).switch_visibility.isChecked && locationPermissionGranted())
                    Snackbar.make(requireActivity().findViewById(android.R.id.content), "Пожалуйста, включите режим поиска для нахождения питомцев", Snackbar.LENGTH_LONG).show()
                //Permission получен, режим поиска включен
                //TODO Перенести логику в презентер (если возможно)
                if ((activity as MainNavigationActivity).switch_visibility.isChecked && locationPermissionGranted()){
                    val bottomSheetDialog = BottomSheetDialog(activity as Context, R.style.BottomSheetDialogTheme)
                    val bottomSheetView = LayoutInflater
                            .from(activity?.applicationContext)
                            .inflate(R.layout.test_layout_bottom_sheet, bottomSheetContainer)
                    bottomSheetView.findViewById<SeekBar>(R.id.seekBar).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            bottomSheetView.findViewById<TextView>(R.id.seekbar_progress).text = "${(seekBar?.progress?.times(50)).toString()} метров"
                            //Отрисовка круга на карте
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
            }
            R.id.button_create_walk -> {
                (activity as MainNavigationActivity).navigateToWalkCreation(currentId)
            }
        }
    }

    /**
     * Инициализация презентера
     */
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
}