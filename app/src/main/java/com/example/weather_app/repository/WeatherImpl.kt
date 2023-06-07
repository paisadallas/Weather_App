package com.example.weather_app.repository

import com.example.weather_app.model.WeatherCity
import io.reactivex.Single
import javax.inject.Inject

class WeatherImpl @Inject constructor(
    private val weatherAPI: API
) : WeatherRepository {

    // Implementation of the WeatherRepository interface

    // This method fetches the weather data for a given city query
    override fun getCityWeather(cityQuery: String): Single<WeatherCity> {
        // Call the getCityWeather method of the weatherAPI with the city query and API key
        return weatherAPI.getCityWeather(cityQuery,API.KEY)
    }
}