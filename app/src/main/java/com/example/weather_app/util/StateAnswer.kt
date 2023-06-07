package com.example.weather_app.util

import com.example.weather_app.model.WeatherCity

sealed class StateAnswer {

    object LOADING : StateAnswer()

    data class WEATHER(val weatherCity: WeatherCity) : StateAnswer()

    data class ERROR(val error: Throwable) : StateAnswer()
}