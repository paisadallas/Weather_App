package com.example.weather_app.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.weather_app.R
import com.example.weather_app.databinding.FragmentHomeBinding
import com.example.weather_app.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModelHome : HomeFragmentViewModel by viewModels()

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewModelHome.weatherCity.observe(viewLifecycleOwner){
            Log.d("WEATHER_TAG",it.name)
        }

        viewModelHome.getCityWeather()

        return binding.root
    }

}