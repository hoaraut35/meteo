package com.hoarauthomas.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.hoarauthomas.weather.api.ResponseWeather
import com.hoarauthomas.weather.repositories.WeatherAPIRepository
import com.hoarauthomas.weather.ui.detailcity.DetailCityViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness

@RunWith(MockitoJUnitRunner::class)
class DetailCityViewModelTest {

    //to run each task synchronously...
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //to view all warnings...
    @Rule
    @JvmField
    val initRule:MockitoRule = MockitoJUnit.rule()

    //to mock repository...
    private var myWeatherMockAPIRepository : WeatherAPIRepository = Mockito.mock(WeatherAPIRepository::class.java)

    //to mock viewModel...
    private var  myDetailCityViewModel : DetailCityViewModel? = null

    //to mock livedata...
    private val myResponseWeather = MutableLiveData<ResponseWeather>()

    @Before
    fun setup() {

        //set fake data to livedata...
        myResponseWeather.value = ResponseWeather(visibility = 100)

        //mock repository function...
        Mockito.`when`(myWeatherMockAPIRepository.getCityWeatherAPIResponse()).thenReturn(myResponseWeather)

        //set viewModel...
        myDetailCityViewModel = DetailCityViewModel(myWeatherMockAPIRepository)

        //set viewstate...
        //myViewState = LiveDataTestUtils.getOrAwaitValue(myViewModel.getViewStateForUI())
    }

//    @Test
//    fun checkViewState(){
//        assertEquals(xx, myViewState.myVisibilty)
//    }

    @Test
    fun checkVisibility(){
        val response = myDetailCityViewModel?.weatherLiveData()?.value
        assertEquals(100, response?.visibility)
    }
}