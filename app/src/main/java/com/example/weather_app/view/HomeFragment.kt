package com.example.weather_app.view

import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.weather_app.R
import com.example.weather_app.databinding.FragmentHomeBinding
import com.example.weather_app.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment(), LocationListener {

    private val viewModelHome : HomeFragmentViewModel by viewModels()

    private lateinit var locationManager: LocationManager
    private lateinit var context: Context

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Location
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        viewModelHome.weatherCity.observe(viewLifecycleOwner){
            Log.d("WEATHER_TAG",it.name)
        }

        viewModelHome.getCityWeather()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }else {
            startLocationUpdates()
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED){
            return
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            MIN_TIME_BETWEEN_UPDATES,
            MIN_DISTANCE_CHANGE_FOR_UPDATES,
            this
            )
    }

    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: List<Address> =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)!!

        if (addresses.isNotEmpty()) {
            val city = addresses[0].locality
            Toast.makeText(requireContext(), "Current city $city", Toast.LENGTH_SHORT).show()
            binding.etCity.setText(city)
            viewModelHome.currentCityLiveData.observe(viewLifecycleOwner){
                 city
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Location permission denied. Unable to get current city.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
        private const val MIN_TIME_BETWEEN_UPDATES: Long = 1000 // in milliseconds
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 10f // in meters
    }

}