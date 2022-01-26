package com.hoarauthomas.weather.api

import com.hoarauthomas.weather.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //https://developers.google.com/maps/documentation/android-sdk/get-api-key
    //https://openweathermap.org/
    @GET("weather?appid=${BuildConfig.OPENWEATHER_API_KEY}&units=metric&lang=fr")
    fun callWeatherByCity(
        @Query("q") cityAndCountry: String
    ): Call<ResponseWeather>

}