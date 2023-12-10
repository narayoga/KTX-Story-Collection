package com.example.talesoftheunknown.view.Maps

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.talesoftheunknown.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.talesoftheunknown.databinding.ActivityMapsBinding
import com.example.talesoftheunknown.view.ViewModelFactory
import com.google.android.gms.maps.model.LatLngBounds

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel by viewModels<MapsViewModel> {
        ViewModelFactory.getInstance(this, null )
    }
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val boundsBuilder = LatLngBounds.Builder()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getMyLocation()
        addManyMarker()
    }
    private fun addManyMarker() {
        viewModel.getLocation()
        viewModel.listStoryLocation.observe(this) { location ->
            Log.d("MapsActivity", "Observer triggered with data: $location")
            location.forEach { data ->
                val latLng = LatLng(data.lat, data.lon)
                mMap.addMarker(
                    MarkerOptions().position(latLng).title(data.name)
                )
                boundsBuilder.include(latLng)
            }
            val bounds: LatLngBounds = boundsBuilder.build()
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    resources.displayMetrics.widthPixels,
                    resources.displayMetrics.heightPixels,
                    300
                )
            )
        }
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_options, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }

            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }

            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }

            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    data class TourismPlace(
        val name: String,
        val latitude: Double,
        val longitude: Double
    )

//    private fun addManyMarker() {
//        val tourismPlace = listOf(
//            TourismPlace("Floating Market Lembang", -6.8168954, 107.6151046),
//            TourismPlace("The Great Asia Africa", -6.8331128, 107.6048483),
//            TourismPlace("Rabbit Town", -6.8668408, 107.608081),
//            TourismPlace("Alun-Alun Kota Bandung", -6.9218518, 107.6025294),
//            TourismPlace("Orchid Forest Cikole", -6.780725, 107.637409),
//        )
//        tourismPlace.forEach { tourism ->
//            val latLng = LatLng(tourism.latitude, tourism.longitude)
//            mMap.addMarker(
//                MarkerOptions().position(latLng).title(tourism.name)
//            )
//            boundsBuilder.include(latLng)
//        }
//        val bounds: LatLngBounds = boundsBuilder.build()
//        mMap.animateCamera(
//            CameraUpdateFactory.newLatLngBounds(
//                bounds,
//                resources.displayMetrics.widthPixels,
//                resources.displayMetrics.heightPixels,
//                300
//            )
//        )
//    }

}