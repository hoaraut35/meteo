package com.hoarauthomas.weather.database

import androidx.room.*
import com.hoarauthomas.weather.models.City
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: City)

    @Update
    suspend fun updateCity(city: City)

    @Delete
    suspend fun deleteCity(city:City)

    @Query("SELECT * FROM city_table")
    fun getCities() : Flow<List<City>>

}