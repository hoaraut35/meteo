package com.hoarauthomas.weather.ui.listCities

import androidx.lifecycle.*
import com.hoarauthomas.weather.api.ResponseWeather
import com.hoarauthomas.weather.models.City
import com.hoarauthomas.weather.repositories.DatabaseRepository
import com.hoarauthomas.weather.repositories.WeatherFirebaseRepository
import com.hoarauthomas.weather.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val weatherRepository: WeatherRepository,
    private val weatherFirebaseRepository: WeatherFirebaseRepository
) :
    ViewModel() {

    fun getCities() = databaseRepository.getCities().asLiveData()


    private var cityInserted = MutableLiveData<Long>()


    //viewstate...
    private val myViewStateCitiesMediator: MediatorLiveData<CitiesViewState> =
        MediatorLiveData<CitiesViewState>()

    //sources...
    var getCities: LiveData<List<City>> = databaseRepository.getCities().asLiveData()

    //init mediator observer with all sources...

    init {

        //first source...
        myViewStateCitiesMediator.addSource(getCities) { listCities ->
            if (listCities != null) {
                combine(listCities)
            }
        }

        //second source..


    }

    //combine all source in viewstate
    private fun combine(myListOfCities: List<City>) {

        val viewState = CitiesViewState()

        //we construct viewstate object...
        //set temperature
        //set cityname
        //set icon


        viewState.cityName = "Paris"
        viewState.cityTemperature ="10"

        //we return the view
         myViewStateCitiesMediator.value = viewState
    }

    //observe by fragment ui
    fun getViewStateForUI(): LiveData<CitiesViewState>{
        return myViewStateCitiesMediator
    }








    fun insertCity(city: City) =
        viewModelScope.launch { cityInserted.value = databaseRepository.insertCity(city) }

    fun cityInsertedLive(): LiveData<Long> {
        return cityInserted
    }

    suspend fun updateCity(city: City) = viewModelScope.launch { databaseRepository.update(city) }

    fun deleteCity(city: City) = viewModelScope.launch { databaseRepository.deleteCity(city) }


    //to query weather...
    fun getWeatherByCity(city: String, country: String) {
        weatherRepository.getWeatherByCity(city, country)
    }

    //to observe weather result ...
    fun weatherLiveData(): LiveData<ResponseWeather> {
        return weatherRepository.getWeatherLiveData()
    }

    //add city to firestore
    fun createCity(city: City) {
        weatherFirebaseRepository.createCity(city)
    }

    //add city detail to firestore
    fun createCityDetail(responseCityDetail: ResponseWeather, cityAndCountry: String) {
        weatherFirebaseRepository.createCityWeatherDetail(responseCityDetail, cityAndCountry)
    }

    //get city detail
    fun getCityDetail(): LiveData<List<ResponseWeather>> {
        return weatherFirebaseRepository.getCityDetail()
    }

    fun callCityDetail(cityAndCountry: String) {
        weatherFirebaseRepository.callAllCitiesInfoRequest(cityAndCountry)
    }



}