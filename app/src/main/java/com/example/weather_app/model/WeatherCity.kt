package com.example.weather_app.model

import com.google.gson.annotations.SerializedName

data class WeatherCity(
    @SerializedName("name")
    val name: String,
    val cod: Int,
    val id: String,
    val weather: ArrayList<Weather>
)