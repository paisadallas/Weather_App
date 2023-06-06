package com.example.weather_app.repository

import com.example.weather_app.model.WeatherCity
import io.reactivex.Single
import javax.inject.Inject

class WeatherImpl @Inject constructor(
    private val weatherAPI: API
) : WeatherRepository {
    override fun getCityWeather(cityQuery: String): Single<WeatherCity> {
        return weatherAPI.getCityWeather(cityQuery,API.KEY)
    }
}