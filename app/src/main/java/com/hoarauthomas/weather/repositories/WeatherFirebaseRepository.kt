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
class WeatherFirebaseRepository  @Inject constructor() {

    private val weatherCollection : String  = "weather"
    private val weatherCollectionDetail : String  = "weatherDetail"
    private val mutableOpenWeatherResponseList : MutableLiveData<List<ResponseWeather>> = MutableLiveData<List<ResponseWeather>>()

    private fun getWeatherCollection() : CollectionReference {
        return FirebaseFirestore.getInstance().collection(weatherCollection)
    }

    fun getAllWeather(): LiveData<List<City>> {

        val myCitiesListFromFirestore: MutableLiveData<List<City>> = MutableLiveData<List<City>>()

        this.getWeatherCollection().addSnapshotListener { value, error ->

            val myListCities : MutableList<City> = ArrayList<City>()

            if (value != null)
            {
             for (document in value.documents){
                 myListCities.add(document.toObject(City::class.java)!!)
             }


            }
        }

        return myCitiesListFromFirestore
    }


    fun createCity(city: City){
        this.getWeatherCollection().document(city.name.toString()+"-" + city.countryCode).set(city)
            .addOnSuccessListener {Log.i("[API]","success") }
            .addOnFailureListener { Log.i("[API]","error") }
    }

    //get the detail info for a city
    fun getWeatherInfoCollection(cityAndCountryName: String): CollectionReference {
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

    // Get All Cities Info
    fun callAllCitiesInfoRequest(cityAndCountryName: String) {

        getWeatherInfoCollection(cityAndCountryName)
            .addSnapshotListener { value: QuerySnapshot?, error: FirebaseFirestoreException? ->
                val openWeatherResponseList: MutableList<ResponseWeather> =
                    ArrayList<ResponseWeather>()

                if (value != null) {
                    for (document in value.documents) {
                        val openWeatherResponse: ResponseWeather? =
                            document.toObject(ResponseWeather::class.java)
                        openWeatherResponseList.add(openWeatherResponse!!)
                    }
                }

                mutableOpenWeatherResponseList.setValue(openWeatherResponseList)
            }
        return
    }

    fun getCityDetail():LiveData<List<ResponseWeather>>{
        return mutableOpenWeatherResponseList
    }

}