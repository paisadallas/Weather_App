package com.example.weather_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.model.WeatherCity
import com.example.weather_app.repository.WeatherRepository
import com.example.weather_app.usecase.GetWeatherUseCase
import com.example.weather_app.util.Resource
import com.example.weather_app.util.StateAnswer
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {


    private val TAG = "WEATHER_TAG"

    private val _weatherCity: MediatorLiveData<Resource<WeatherCity>?> =
        MediatorLiveData()
    val weatherCity: LiveData<Resource<WeatherCity>?> get() = _weatherCity


    private var _currentCityMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val currentCityLiveData : LiveData<String> get() = _currentCityMutableLiveData

    //Reload data
    private var _reloadMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        postValue(true)
    }
    val reloadLiveData : LiveData<Boolean> get() = _reloadMutableLiveData

    // Method to update the reloadLiveData value
    fun reloadData(reload : Boolean ){
        viewModelScope.launch {
            _reloadMutableLiveData.postValue(reload)
        }
    }

    // Method to update the currentCityLiveData value
    fun updateCity(city:String){

        viewModelScope.launch {
            _currentCityMutableLiveData.value = city
            Log.d(TAG,currentCityLiveData.value.toString())
        }
    }

    // Method to fetch weather data using the getWeatherUseCase
    fun getMutableLiveData(){
       viewModelScope.launch {
           val responseWeather = getWeatherUseCase.invoke(currentCityLiveData.value.toString())
            _weatherCity.addSource(responseWeather){
                _weatherCity.postValue(it)
            }
       }
    }
}