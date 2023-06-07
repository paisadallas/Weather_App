package com.example.weather_app.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.weather_app.databinding.FragmentHomeBinding
import com.example.weather_app.util.Resource
import com.example.weather_app.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Logger

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModelHome : HomeFragmentViewModel by viewModels()

    private val TAG2 = "WEATHER_TAG_LIVEDATA"

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModelHome.weatherCity.observe(viewLifecycleOwner){

            when(it){
                is Resource.Loading -> {
                    Log.d(TAG2, "Loading...")
                }
                is Resource.Success -> {
                    Log.d(TAG2, it.data?.name ?: "NUll on Fragment")
                }

                is Resource.Error ->{
                    it.throwable?.let {
                        it.localizedMessage?.let { it1 -> Logger.getLogger(it1) }
                    }
                }
                else ->{
                    // no operator
                }
            }

        }

        viewModelHome.getMutableLiveData()

        return binding.root
    }

}