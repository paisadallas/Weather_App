package com.example.weather_app.view

import android.content.Context

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.example.weather_app.R
import com.example.weather_app.databinding.FragmentHomeBinding
import com.example.weather_app.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }

}