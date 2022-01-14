package com.hoarauthomas.weather.ui.detailcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hoarauthomas.weather.R
import com.hoarauthomas.weather.databinding.FragmentCityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class CityDetails : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private val viewModelWeather: DetailCityViewModel by viewModels()

    private lateinit var binding: FragmentCityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            if (param1 != null) {
                viewModelWeather.getWeatherByCity(param1.toString(), "fr")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCityDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.isVisible = false

        viewModelWeather.weatherLiveDataFlag().observe(viewLifecycleOwner) {
            if(it){
                binding.progressHorizontal.show()
                binding.cityName.text = ""
                binding.cityTemperature.text = ""
                binding.cityPressure.text = ""
                binding.cityIconText.text = ""
                binding.weatherIcon.isVisible = false
            }else{
                binding.progressHorizontal.hide()
            }

        }

        //observe data...
        viewModelWeather.weatherLiveData().observe(viewLifecycleOwner) {

                //binding.progressHorizontal.hide()
                binding.cityName.text = it.name.toString()
                binding.cityTemperature.text = "${it.main?.temp.toString()} °C"
                binding.cityPressure.text = "${it.main?.pressure.toString()} hPa"

                //https://openweathermap.org/weather-conditions
                binding.weatherIcon.isVisible = true
                Glide.with(binding.weatherIcon)
                    .load("http://openweathermap.org/img/wn/" + it.weather?.get(0)?.icon + "@2x.png")
                    .centerCrop()
                    .into(binding.weatherIcon)

                binding.cityIconText.text = it.weather?.get(0)?.main.toString()

        }

        return view

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}