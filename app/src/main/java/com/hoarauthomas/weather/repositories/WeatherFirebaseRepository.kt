package com.hoarauthomas.weather.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.hoarauthomas.weather.api.ResponseWeather
import com.hoarauthomas.weather.models.City
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherFirebaseRepository @Inject constructor() {

    val weatherListResultFirestore = mutableListOf<ResponseWeather>()

    private val weatherCollection: String = "weather"
    private val weatherCollectionDetail: String = "weatherDetail"
    private val mutableOpenWeatherFirestore: MutableLiveData<ResponseWeather> =
        MutableLiveData<ResponseWeather>()

    private fun getWeatherCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(weatherCollection)
    }

    fun getAllWeather(): LiveData<List<City>> {

        val myCitiesListFromFirestore: MutableLiveData<List<City>> = MutableLiveData<List<City>>()

        this.getWeatherCollection().addSnapshotListener { value, _ ->

            val myListCities: MutableList<City> = ArrayList()

            if (value != null) {
                for (document in value.documents) {
                    myListCities.add(document.toObject(City::class.java)!!)
                }

            }
        }

        return myCitiesListFromFirestore
    }

    fun createCity(city: City) {
        this.getWeatherCollection().document(city.name.toString() + "-" + city.countryCode)
            .set(city)
            .addOnSuccessListener { Log.i("[API]", "success") }
            .addOnFailureListener { Log.i("[API]", "error") }
    }

    //get the detail info for a city
    private fun getWeatherInfoCollection(cityAndCountryName: String): CollectionReference {
        return FirebaseFirestore.getInstance().collection(weatherCollection)
            .document(cityAndCountryName)
            .collection(
                weatherCollectionDetail
            )
    }

    //create cityDetail
    fun createCityWeatherDetail(
        openWeatherResponse: ResponseWeather,
        cityAndCountryName: String
    ) {
        getWeatherInfoCollection(cityAndCountryName).document(cityAndCountryName)
            .set(openWeatherResponse)
    }

    fun getCityDataFirestore(cityAndCountryName: String) {
        getWeatherInfoCollection(cityAndCountryName)
            .addSnapshotListener { value: QuerySnapshot?, _: FirebaseFirestoreException? ->
                if (value != null) {
                    for (document in value.documents) {
                        val openWeatherResponse: ResponseWeather? =
                            document.toObject(ResponseWeather::class.java)
                        mutableOpenWeatherFirestore.value = openWeatherResponse!!
                    }
                }
            }
    }

    // all data cities
    fun getAllDataCityFirestore(): LiveData<ResponseWeather> {
        return mutableOpenWeatherFirestore
    }

}