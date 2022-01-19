package com.hoarauthomas.weather.ui.listCities

import androidx.lifecycle.*
import com.hoarauthomas.weather.api.ResponseWeather
import com.hoarauthomas.weather.models.City
import com.hoarauthomas.weather.repositories.DatabaseRepository
import com.hoarauthomas.weather.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val databaseRepository: DatabaseRepository, private val weatherRepository: WeatherRepository) :
    ViewModel() {

    fun getCities() = databaseRepository.getCities().asLiveData()

    private var cityInserted = MutableLiveData<Long>()

    fun insertCity(city: City) = viewModelScope.launch { cityInserted.value = databaseRepository.insertCity(city)}

    fun cityInsertedLive(): LiveData<Long>{
        return cityInserted
    }

    suspend fun updateCity(city: City) = viewModelScope.launch { databaseRepository.update(city) }

    fun deleteCity(city: City) = viewModelScope.launch { databaseRepository.deleteCity(city) }



    //to query weather...
    fun getWeatherByCity(city: String, country:String){
        weatherRepository.getWeatherByCity(city,country)
    }

    //to observe weather result ...
    fun weatherLiveData(): LiveData<ResponseWeather> {
        return weatherRepository.getWeatherLiveData()
    }

}