package com.example.licenta.fragment.main

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.activity.result.contract.ActivityResultContracts
import com.example.licenta.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import java.lang.Exception

class MapsFragment : Fragment() {

    private val locationRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        for (entry in map.entries) {
            when (entry.key) {
                Manifest.permission.ACCESS_FINE_LOCATION -> setUpMap()
                Manifest.permission.ACCESS_COARSE_LOCATION -> setUpMap()
                else -> requestLocationPermission()
            }
        }
    }

    private lateinit var mapView: View
    private lateinit var placesClient: PlacesClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var googleMap: GoogleMap? = null
    private var locationGranted = false
    private var lastKnownLocation : Location? = null
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        requestLocationPermission()
        moveLocationButtonDown()
        this.googleMap = googleMap
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapView = mapFragment!!.view!!
        mapFragment.getMapAsync(callback)
    }

    private fun setUpMap() {
        locationGranted = true
        setUpClient()
        updateUserLocation()
        getDeviceLocation()
    }

    private fun setUpClient() {
        Places.initialize(context!!, getString(R.string.google_maps_key))
        placesClient = Places.createClient(context!!)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context!!)
    }

    @SuppressLint("MissingPermission")
    private fun updateUserLocation() {
        if (googleMap == null)
            return
        try {
            if(locationGranted) {
                googleMap!!.isMyLocationEnabled = true
                googleMap!!.uiSettings.isMyLocationButtonEnabled = true
            }else{
                googleMap!!.isMyLocationEnabled = false
                googleMap!!.uiSettings.isMyLocationButtonEnabled = false
                requestLocationPermission()
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        try {
            if (locationGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            val location = LatLng(lastKnownLocation!!.latitude,lastKnownLocation!!.longitude)
                            googleMap?.addMarker(MarkerOptions().position(location).title("Marker in myPlace"))
                            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        googleMap?.addMarker(MarkerOptions().position(defaultLocation).title("Marker in Sydney"))
                        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation))
                        googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun requestLocationPermission() {
        locationRequestLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun moveLocationButtonDown() {
        if (mapView.findViewById<View>(Integer.parseInt("1")) != null) {
            val locationButton = (mapView.findViewById<View>("1".toInt())
                .parent as View).findViewById<View>("2".toInt())

            // and next place it, on bottom right (as Google Maps app)
            val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 150);
        }
    }

    companion object{
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

        private const val M_MAX_ENTRIES = 5
    }
}