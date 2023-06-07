package com.example.weather_app.usecase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather_app.model.WeatherCity
import com.example.weather_app.repository.WeatherRepository
import com.example.weather_app.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    private val TAG = "WEATHER_TAG_USE_CASE"

    fun invoke() : LiveData<Resource<WeatherCity>> {
        val setWeatherMutableLiveData = MutableLiveData<Resource<WeatherCity>>()
        weatherRepository.run {
            getCityWeather("Dallas")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                           setWeatherMutableLiveData.postValue(Resource.Success(it))
                    Log.d(TAG, it.name)
                },{
                    Log.e(TAG,it.localizedMessage)
                })
        }
        return setWeatherMutableLiveData
    }
}