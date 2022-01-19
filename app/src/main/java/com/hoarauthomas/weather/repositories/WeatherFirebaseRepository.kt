package com.hoarauthomas.weather.repositories

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.hoarauthomas.weather.models.City
import javax.inject.Inject
import javax.inject.Singleton
import androidx.lifecycle.MutableLiveData




@Singleton
class WeatherFirebaseRepository  @Inject constructor() {

    private val weatherCollection : String  = "weather"


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





}