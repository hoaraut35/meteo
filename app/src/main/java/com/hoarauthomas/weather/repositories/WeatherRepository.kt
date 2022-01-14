package com.hoarauthomas.weather.repositories

import android.util.Log
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
    private var mutableLiveDataWeatherSearchFlag = MutableLiveData<Boolean>()

    //for ViewModel...
    fun getWeatherLiveData(): LiveData<ResponseWeather>{
        return mutableLiveDataWeather
    }

    //get flag
    fun getFlag():LiveData<Boolean>{
        return mutableLiveDataWeatherSearchFlag
    }

    //for query API...
    fun getWeatherByCity(city: String, country: String){

        val cityAndCountry = "$city,$country"

        weatherAPI.getWeatherByCity(cityAndCountry).enqueue(object : Callback<ResponseWeather> {

            override fun onResponse(
                call: Call<ResponseWeather>,
                response: Response<ResponseWeather>
            ) {
                if (response.isSuccessful){
                    mutableLiveDataWeather.value = response.body()!!
                    mutableLiveDataWeatherSearchFlag.value = false
                }

            }

            override fun onFailure(call: Call<ResponseWeather>, t: Throwable) {

                   mutableLiveDataWeatherSearchFlag.value = true

            }

        })
    }

}