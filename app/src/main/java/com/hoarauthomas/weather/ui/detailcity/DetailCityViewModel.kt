package com.hoarauthomas.weather.ui.detailcity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hoarauthomas.weather.api.ResponseWeather
import com.hoarauthomas.weather.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailCityViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    //to query weather...
    fun getWeatherByCity(city: String, country:String){
        weatherRepository.getWeatherByCity(city,country)
    }

    //to observe weather...
    fun weatherLiveData(): LiveData<ResponseWeather> {
        return weatherRepository.getWeatherLiveData()
    }

}