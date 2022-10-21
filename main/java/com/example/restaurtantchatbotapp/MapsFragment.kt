package com.example.restaurtantchatbotapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/*
   Class MapsFragment displays our google map fragment as a widget
   We have the method OnMapeReadyCallback which the parameter is googlemap aka mMap with type GoogleMap of gogle
   Oncreate we view that xml file with the fragment

    */
class MapsFragment : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.map, mapFragment)
                .commit()
        mapFragment.getMapAsync(this)
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Kuwait, Mishref.
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in restaurant aka fastnForYou and move the camera
        val restaurant = LatLng(29.2761, 48.0655)
        mMap.addMarker(MarkerOptions().position(restaurant).title("FastnForYou"))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(restaurant))
        mMap.uiSettings.isZoomControlsEnabled = true
    }
}
