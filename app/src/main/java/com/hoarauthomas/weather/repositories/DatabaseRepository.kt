package com.hoarauthomas.weather.repositories

import com.hoarauthomas.weather.database.WeatherDao
import com.hoarauthomas.weather.models.City
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(private val weatherDao: WeatherDao) {

    suspend fun insertCity(city: City) = weatherDao.insertCity(city)
    suspend fun deleteCity(city: City) = weatherDao.deleteCity(city)
    suspend fun update(city: City) = weatherDao.updateCity(city)
    fun getCities() = weatherDao.getCities()

}