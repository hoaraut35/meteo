package com.hoarauthomas.weather.ui.detailcity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hoarauthomas.weather.api.ResponseWeather
import com.hoarauthomas.weather.repositories.WeatherAPIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailCityViewModel @Inject constructor(private val weatherAPIRepository: WeatherAPIRepository) :
    ViewModel() {

    //to query weather...
    fun getWeatherByCity(city: String, country:String){
        weatherAPIRepository.getWeatherByCity(city,country)
    }

    //to observe weather result ...
    fun weatherLiveData(): LiveData<ResponseWeather> {
        return weatherAPIRepository.getCityWeatherAPIResponse()
    }

    //to observe search weather flag...
    fun weatherLiveDataFlag(): LiveData<Boolean> {
        return weatherAPIRepository.getFlag()
    }

}