package com.example.weather_app.repository

import com.example.weather_app.model.WeatherCity
import io.reactivex.Single

interface WeatherRepository {
    fun getCityWeather(cityQuery: String): Single<WeatherCity>
}