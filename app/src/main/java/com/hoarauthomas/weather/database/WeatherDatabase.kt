package com.hoarauthomas.weather.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hoarauthomas.weather.injection.ApplicationScope
import com.hoarauthomas.weather.models.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [City::class],
    version = 1,
    exportSchema = false
)

abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    class Callback @Inject constructor(
        private val database: Provider<WeatherDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().weatherDao()

            applicationScope.launch {

                dao.insertCity(City(name = "Rennes"))

            }

        }

    }

}