package com.hoarauthomas.weather.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoarauthomas.weather.api.ResponseWeather
import com.hoarauthomas.weather.api.WeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherAPI : WeatherAPI) {

    private var mutableLiveDataWeather = MutableLiveData<ResponseWeather>()

    //for ViewModel...
    fun getWeatherLiveData(): LiveData<ResponseWeather>{
        return mutableLiveDataWeather
    }

    //for query API...
    fun getWeatherByCity(city: String, country: String){
        weatherAPI.getWeatherByCity(city, country).enqueue(object : Callback<ResponseWeather> {

            override fun onResponse(
                call: Call<ResponseWeather>,
                response: Response<ResponseWeather>
            ) {
                if (response.isSuccessful){
                    mutableLiveDataWeather.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<ResponseWeather>, t: Throwable) {

            }

        })
    }




}