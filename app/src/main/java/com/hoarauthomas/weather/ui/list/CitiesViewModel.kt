package com.hoarauthomas.weather.ui.list

import androidx.lifecycle.*
import com.hoarauthomas.weather.models.City
import com.hoarauthomas.weather.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModel() {

    fun getCities() = databaseRepository.getCities().asLiveData()

    private var cityInserted = MutableLiveData<Long>()

    fun insertCity(city: City) = viewModelScope.launch { cityInserted.value = databaseRepository.insertCity(city)}

    fun cityInsertedLive(): LiveData<Long>{
        return cityInserted
    }

    suspend fun updateCity(city: City) = viewModelScope.launch { databaseRepository.update(city) }

    fun deleteCity(city: City) = viewModelScope.launch { databaseRepository.deleteCity(city) }

}