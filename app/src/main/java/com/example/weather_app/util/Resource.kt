package com.example.weather_app.util

import retrofit2.Call

sealed class Resource<T>(val data: T? = null, val throwable: Throwable? = null, val message: String? = null, val call: Call<T>? = null) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, throwable: Throwable? = null, data: T? = null, call: Call<T>? = null): Resource<T>(data, throwable, message, call)
}