package com.example.weather_app.repository

import com.example.weather_app.model.WeatherCity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("weather")
    fun getCityWeather(@Query("q") cityQuery: String, @Query("appid") apiKey : String = KEY): Single<WeatherCity>

    companion object{
        val KEY: String = "cc01ca8b30e0e67f466a01e32cc58f01"
        val BASE_URL : String = "https://api.openweathermap.org/data/2.5/"
    }
}