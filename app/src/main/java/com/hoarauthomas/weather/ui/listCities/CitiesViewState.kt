package com.hoarauthomas.weather.ui.listCities

import com.hoarauthomas.weather.api.ResponseWeather
import com.hoarauthomas.weather.models.City

data class CitiesViewState (

    var cityTemperature : String? = null,
    var citiesList : List<City>? = null,
    var citiesListAPIResponse : List<ResponseWeather>? = null

)