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

    //public
    val weatherListResultFirestore = mutableListOf<ResponseWeather>()

    //private
    private val weatherCollection: String = "weather"
    private val weatherCollectionDetail: String = "weatherDetail"
    private val mutableOpenWeatherFirestore: MutableLiveData<ResponseWeather> =
        MutableLiveData<ResponseWeather>()

    private fun getWeatherCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(weatherCollection)
    }

    //get the detail info for a city
    private fun getWeatherInfoCollection(cityAndCountryName: String): CollectionReference {
        return FirebaseFirestore.getInstance().collection(weatherCollection)
            .document(cityAndCountryName)
            .collection(
                weatherCollectionDetail
            )
    }

    fun getAllCitiesFirestore(): LiveData<List<City>> {

        val myCitiesListFromFirestore: MutableLiveData<List<City>> = MutableLiveData<List<City>>()

        this.getWeatherCollection().addSnapshotListener { value, _ ->

            val myListCities: MutableList<City> = ArrayList()

            if (value != null) {
                for (document in value.documents) {
                    myListCities.add(document.toObject(City::class.java)!!)

                }
            }
            myCitiesListFromFirestore.setValue(myListCities)
        }

        return myCitiesListFromFirestore
    }

    fun createCity(city: City) {
        this.getWeatherCollection().document(city.name.toString() + "-" + city.countryCode)
            .set(city)
            .addOnSuccessListener { Log.i("[API]", "success") }
            .addOnFailureListener { Log.i("[API]", "error") }
    }



    //create cityDetail
    fun createCityDataWeatherFirestore(
        openWeatherResponse: ResponseWeather,
        cityAndCountryName: String
    ) {
        getWeatherInfoCollection(cityAndCountryName).document(cityAndCountryName)
            .set(openWeatherResponse)
    }

    fun callCityDataRequestFirestore(cityAndCountryName: String) {
        getWeatherInfoCollection(cityAndCountryName)
            .addSnapshotListener { value: QuerySnapshot?, _: FirebaseFirestoreException? ->
                if (value != null) {
                    for (document in value.documents) {
                        val openWeatherResponse: ResponseWeather? =
                            document.toObject(ResponseWeather::class.java)
                        mutableOpenWeatherFirestore.value = openWeatherResponse!!
                    }
                }else
                {
                    Log.i("[API]","Error request API openweather")
                }
            }
    }

    // all data cities
    fun getCityDataFirestore(): LiveData<ResponseWeather> {
        return mutableOpenWeatherFirestore
    }

}