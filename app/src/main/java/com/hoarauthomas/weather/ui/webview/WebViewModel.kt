package com.hoarauthomas.weather.ui.webview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hoarauthomas.weather.models.City
import com.hoarauthomas.weather.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    fun getCitiesRoom() : LiveData<List<City>> = databaseRepository.getCities().asLiveData()



}