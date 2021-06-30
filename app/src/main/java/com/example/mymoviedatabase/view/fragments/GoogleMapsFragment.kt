package com.example.mymoviedatabase.view.fragments

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.mymoviedatabase.R
import com.example.mymoviedatabase.databinding.FragmentGoogleMapsMainBinding
import com.example.tmdbdata.ArtistTest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_google_maps_main.*
import java.io.IOException

class GoogleMapsFragment : Fragment() {

    private var _binding: FragmentGoogleMapsMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private val markers: ArrayList<Marker> = arrayListOf()
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

            setFragmentResultListener("placeOfBirth") { requestKey, bundle ->

            val placeOfBirth = (bundle.getString("coordinates"))

            binding.textAddress.text = placeOfBirth

            val latFM = (bundle.getDouble("lat"))
            val longFM = (bundle.getDouble("long"))

            val initialPlace = LatLng(latFM, longFM)
            googleMap.addMarker(
                    MarkerOptions().position(initialPlace).title(getString(R.string.marker_start))
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPlace, 15F))

        }

    googleMap.setOnMapClickListener { latLng ->
            googleMap.clear()
            googleMap.addMarker(
                    MarkerOptions().position(latLng)
            )
            binding.textAddress.text = getAddressAsync(latLng).toString()
        }

        googleMap.setOnMapLongClickListener { latLng ->
            getAddressAsync(latLng)
            addMarkerToArray(latLng)
            drawLine()
        }
        activateMyLocation(googleMap)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGoogleMapsMainBinding.inflate(inflater, container, false)
        binding.textAddress.setOnClickListener {
            markers.clear()
            map.clear()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initSearchByAddress()
    }

    private fun initSearchByAddress() {
        binding.buttonSearch.setOnClickListener {
            val geoCoder = Geocoder(it.context)
            val searchText = searchAddress.text.toString()
            Thread {
                try {
                    val addresses = geoCoder.getFromLocationName(searchText, 1)
                    if (addresses.size > 0) {
                        goToAddress(addresses, it, searchText)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun goToAddress(
            addresses: MutableList<Address>,
            view: View,
            searchText: String
    ) {
        val location = LatLng(
                addresses[0].latitude,
                addresses[0].longitude
        )
        view.post {
            setMarker(location, searchText, R.drawable.ic_launcher_background)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            location,
                            15f
                    )
            )
        }
    }

    private fun getAddressAsync(location: LatLng) {
        context?.let {
            val geoCoder = Geocoder(it)
            Thread {
                try {
                    val addresses =
                            geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                    textAddress.post { textAddress.text = addresses[0].getAddressLine(0) }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun addMarkerToArray(location: LatLng) {
        val marker = setMarker(location, markers.size.toString(), R.drawable.ic_android_black_24dp)
        markers.add(marker)
    }

    private fun setMarker(
            location: LatLng,
            searchText: String,
            resourceId: Int
    ): Marker {
        return map.addMarker(
                MarkerOptions()
                        .position(location)
                        .title(searchText)
                        .icon(generateBitmapDescriptorFromRes(context, R.drawable.ic_arrow))
        )
    }

    fun generateBitmapDescriptorFromRes(
            context: Context?, resId: Int): BitmapDescriptor? {
        val drawable = context?.let { ContextCompat.getDrawable(it, resId) }
        drawable!!.setBounds(
                0,
                0,
                drawable.intrinsicWidth,
                drawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                    PolylineOptions()
                            .add(previous, current)
                            .color(Color.RED)
                            .width(5f)
            )
        }
    }

    private fun activateMyLocation(googleMap: GoogleMap) {
        context?.let {
            val isPermissionGranted =
                    ContextCompat.checkSelfPermission(it, ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED
            googleMap.isMyLocationEnabled = isPermissionGranted
            googleMap.uiSettings.isMyLocationButtonEnabled = isPermissionGranted
        }
        //Получить разрешение, если его нет
    }

    companion object {
        const val BUNDLE_EXTRA = "placeOfBirth"
        fun newInstance(bundle: Bundle): GoogleMapsFragment {
            val fragment = GoogleMapsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}
