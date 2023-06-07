package com.example.weather_app.di

import com.example.weather_app.repository.API
import com.example.weather_app.repository.WeatherImpl
import com.example.weather_app.repository.WeatherRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesGson() : Gson = Gson()

    @Provides
    fun providesLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun injectRetrofitAPI(okHttpClient: OkHttpClient, gson: Gson) : API {
        return Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(API::class.java)
    }

    @Provides
    fun provideRepository(api: API) : WeatherRepository {
        return WeatherImpl(api)
    }


}