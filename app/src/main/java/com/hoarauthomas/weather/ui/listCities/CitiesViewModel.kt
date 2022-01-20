package com.hoarauthomas.weather.ui.listCities

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

    private var getCityDataAPI: LiveData<ResponseWeather> =
        weatherAPIRepository.getCityWeatherAPIResponse()

    private var getCityDataFirestore: LiveData<ResponseWeather> =
        weatherFirebaseRepository.getAllDataCityFirestore()

    //init mediator observer with all sources...
    init {

        //first source : Room cities list ...
        myViewStateCitiesMediator.addSource(getCitiesRoom) { listCities ->
            if (listCities != null) {
                combine(listCities, getCityDataAPI.value, weatherFirebaseRepository.weatherListResultFirestose)
            }
        }

        //second source : city data api
        myViewStateCitiesMediator.addSource(getCityDataAPI) { cityDataResponse ->
            if (cityDataResponse != null) {
                combine(getCitiesRoom.value, cityDataResponse, weatherFirebaseRepository.weatherListResultFirestose)
            }
        }

        //third source : get all cities data from firebase
        myViewStateCitiesMediator.addSource(getCityDataFirestore) { weatherCityResultFirestore ->
            if (weatherCityResultFirestore != null) {
                weatherFirebaseRepository.weatherListResultFirestose.add(weatherCityResultFirestore)
                combine(getCitiesRoom.value, getCityDataAPI.value, weatherFirebaseRepository.weatherListResultFirestose)
            }
        }
    }

    //combine all source in viewState
    private fun combine(
        myListOfCitiesRoom: List<City>?,
        myCityData: ResponseWeather?,
        myListCityDataFirestore: List<ResponseWeather>?
    ) {

        //si la liste des villes n'es pas nulle alors...
        if (!myListOfCitiesRoom.isNullOrEmpty()) {

            if (myListCityDataFirestore.isNullOrEmpty()) {
                myListOfCitiesRoom.forEach { myCity ->
                    weatherFirebaseRepository.getCityDataFirestore(myCity.name + "-" + myCity.countryCode)
                }
            }

        }

        //add logic here...
        val viewState = CitiesViewState()
        viewState.cityTemperature = myCityData?.main.toString()
        viewState.citiesList = myListOfCitiesRoom
        viewState.citiesListAPIResponse = myListCityDataFirestore

        //we return the view
        myViewStateCitiesMediator.value = viewState

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
        weatherAPIRepository.getWeatherByCity(city, country)
    }

    //add city to firestore
    fun createCity(city: City) {
        weatherFirebaseRepository.createCity(city)
    }

    //add city detail to firestore
    fun createCityDetail(responseCityDetail: ResponseWeather, cityAndCountry: String) {
        weatherFirebaseRepository.createCityWeatherDetail(responseCityDetail, cityAndCountry)
    }

    //get city detail from api
    // fun getCityDetail(): LiveData<List<ResponseWeather>> {
    //    return weatherFirebaseRepository.getCitiesDetailFirestore()
    // }

    //fun callCityDetail(cityAndCountry: String) {
    //  weatherFirebaseRepository.callAllCitiesInfoRequest(cityAndCountry)
    //  }


}