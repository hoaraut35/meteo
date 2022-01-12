package com.hoarauthomas.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hoarauthomas.weather.models.City
import com.hoarauthomas.weather.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) : ViewModel() {

//    fun getCities() = databaseRepository.getCities().asLiveData()
//    suspend fun insertCity(city: City) = databaseRepository.insertCity(city)
//    suspend fun updateCity(city: City) = databaseRepository.update(city)
//    suspend fun deleteCity(city: City) = databaseRepository.deleteCity(city)

}