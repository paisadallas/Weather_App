package com.example.weather_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_app.model.WeatherCity
import com.example.weather_app.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherCity: MutableLiveData<WeatherCity> =
        MutableLiveData()
    val weatherCity: LiveData<WeatherCity> get() = _weatherCity

    private val _currentCityMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val currentCityLiveData : LiveData<String> get() = _currentCityMutableLiveData

    fun getCityWeather(){
        repository.getCityWeather("Dallas")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("WEATHER_TAG",it.name)
                _weatherCity.value = it
            },{
                Log.d("WEATHER_TAG",it.message.toString())
            })
    }
}