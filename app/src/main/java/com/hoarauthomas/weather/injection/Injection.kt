package com.hoarauthomas.weather.injection

import android.content.Context
import androidx.room.Room
import com.hoarauthomas.weather.api.WeatherAPI
import com.hoarauthomas.weather.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Injection {

    @Provides
    @Singleton
    fun provideRetrofit(): WeatherAPI = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org/data/2.5")
        //.addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherAPI::class.java)


    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context, //to get application context
        callBack: WeatherDatabase.Callback   //to add callback
    ) = Room.databaseBuilder(appContext, WeatherDatabase::class.java, "databasetest")
        .fallbackToDestructiveMigration()
        .addCallback(callBack)
        .build()

    @Provides
    @Singleton
    fun provideDao(database: WeatherDatabase) = database.weatherDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope