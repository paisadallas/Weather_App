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

    /**
     *   // This method is invoked to fetch weather data for a given city query.
     *     // It returns a LiveData object that holds the resource state of the weather data.
     */
    fun invoke(cityQuery: String) : LiveData<Resource<WeatherCity>> {
        // Create a MutableLiveData object to hold the weather data resource
        val setWeatherMutableLiveData = MutableLiveData<Resource<WeatherCity>>()

        // Call the getCityWeather method of the weatherRepository to fetch the weather data
        weatherRepository.run {
            getCityWeather(cityQuery)
                .subscribeOn(Schedulers.io()) // Perform the operation on the I/O thread
                .observeOn(AndroidSchedulers.mainThread()) // Observe the result on the main thread
                .subscribe({
                    // When the weather data is successfully fetched, post a Success resource state
                           setWeatherMutableLiveData.postValue(Resource.Success(it))
                    Log.d(TAG, it.name)
                },{
                    // When an error occurs, post an Error resource state with the error message
                    Log.e(TAG,it.localizedMessage)
                    setWeatherMutableLiveData.postValue(Resource.Error(it.localizedMessage))
                })
        }
        // Return the MutableLiveData object
        return setWeatherMutableLiveData
    }
}