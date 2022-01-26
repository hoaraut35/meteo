package com.hoarauthomas.weather.ui.listCities

import android.util.Log
import androidx.lifecycle.*
import com.hoarauthomas.weather.api.ResponseWeather
import com.hoarauthomas.weather.models.City
import com.hoarauthomas.weather.repositories.DatabaseRepository
import com.hoarauthomas.weather.repositories.WeatherAPIRepository
import com.hoarauthomas.weather.repositories.WeatherFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val weatherAPIRepository: WeatherAPIRepository,
    private val weatherFirebaseRepository: WeatherFirebaseRepository
) :
    ViewModel() {

    //viewState ...
    private val myViewStateCitiesMediator: MediatorLiveData<CitiesViewState> =
        MediatorLiveData<CitiesViewState>()

    //sources...
    private var getCitiesRoom: LiveData<List<City>> = databaseRepository.getCities().asLiveData()
    private var getCitiesFirestore = weatherFirebaseRepository.getAllCitiesFirestore()
    private var getCityDataAPI: LiveData<ResponseWeather> = weatherAPIRepository.getCityWeatherAPIResponse()
    private var getCityDataFirestore: LiveData<ResponseWeather> = weatherFirebaseRepository.getCityDataFirestore()

    //init mediator observer with all sources...
    init {

        //observe Room cities list ...
        myViewStateCitiesMediator.addSource(getCitiesRoom) { listCities ->
            if (listCities != null) {
                combine(
                    listCities,
                    getCitiesFirestore.value,
                    getCityDataAPI.value,
                    weatherFirebaseRepository.weatherListResultFirestore
                )
            }
        }

        //Observe city data api result
        myViewStateCitiesMediator.addSource(getCityDataAPI) { cityDataResponse ->
            if (cityDataResponse != null) {
                combine(
                    getCitiesRoom.value,
                    getCitiesFirestore.value,
                    cityDataResponse,
                    weatherFirebaseRepository.weatherListResultFirestore
                )
            }
        }

        //Observe city weather data from API
        myViewStateCitiesMediator.addSource(getCityDataFirestore) { weatherCityResultFirestore ->
            if (weatherCityResultFirestore != null) {
                weatherFirebaseRepository.weatherListResultFirestore.add(weatherCityResultFirestore)
                combine(
                    getCitiesRoom.value,
                    getCitiesFirestore.value,
                    getCityDataAPI.value,
                    weatherFirebaseRepository.weatherListResultFirestore
                )
            }
        }

        //observe list cities from firebase without weather data
        myViewStateCitiesMediator.addSource(getCitiesFirestore) { citiesFromFirestore ->
            if (citiesFromFirestore != null) {
                combine(
                    getCitiesRoom.value,
                    citiesFromFirestore,
                    getCityDataAPI.value,
                    weatherFirebaseRepository.weatherListResultFirestore,
                )
            }
        }
    }

    //combine all source in viewState
    private fun combine(
        myListOfCitiesRoom: List<City>?,
        myCitiesFromFirestore: List<City>?,
        myCityData: ResponseWeather?,
        myListCityDataFirestore: List<ResponseWeather>?

    ) {


        //add logic here...
        val viewState = CitiesViewState()

        if (!myListOfCitiesRoom.isNullOrEmpty()) {

        }

        viewState.citiesList = myListOfCitiesRoom

        //we return the view
        myViewStateCitiesMediator.value = viewState

        Log.i("","")
    }

    //observe by fragment ui
    fun getViewStateForUI(): LiveData<CitiesViewState> {
        return myViewStateCitiesMediator
    }


    //viewModelCities.createCityDetail(cityData,"${cityData.name}-${cityData.sys?.country}")

    private var cityInserted = MutableLiveData<Long>()

    fun insertCity(city: City) =
        viewModelScope.launch { cityInserted.value = databaseRepository.insertCity(city) }

    fun deleteCity(city: City) = viewModelScope.launch { databaseRepository.deleteCity(city) }

    //to remove
    fun cityInsertedLive(): LiveData<Long> {
        return cityInserted
    }

    //to remove
    suspend fun updateCity(city: City) = viewModelScope.launch { databaseRepository.update(city) }

    //to query weather...
    fun getWeatherByCity(city: String, country: String) {
        weatherAPIRepository.callWeatherByCity(city, country)
    }

    //add city to firestore
    fun createCity(city: City) {
        weatherFirebaseRepository.createCity(city)
    }

    //add city detail to firestore
    fun createCityDetail(responseCityDetail: ResponseWeather, cityAndCountry: String) {
        weatherFirebaseRepository.createCityDataWeatherFirestore(responseCityDetail, cityAndCountry)
    }

    //get city detail from api
    // fun getCityDetail(): LiveData<List<ResponseWeather>> {
    //    return weatherFirebaseRepository.getCitiesDetailFirestore()
    // }

    //fun callCityDetail(cityAndCountry: String) {
    //  weatherFirebaseRepository.callAllCitiesInfoRequest(cityAndCountry)
    //  }


}