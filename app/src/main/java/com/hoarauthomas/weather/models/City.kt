package com.hoarauthomas.weather.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class City(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name : String
)