package com.hoarauthomas.weather.ui.listCities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hoarauthomas.weather.api.ResponseWeather
import com.hoarauthomas.weather.databinding.ItemCityBinding
import com.hoarauthomas.weather.models.City

//class CitiesAdapter(private val cities: List<City>) :
class CitiesAdapter(private val cities:List<City>, private val citiesAPIResult: List<ResponseWeather>) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        var cityNameView = binding.cityNameView
        var cityTemperature = binding.cityTemperatureView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.cityNameView.text = cities[position].name
        holder.cityNameView.tag = cities[position].id

        citiesAPIResult.forEach { response ->
            if (holder.cityNameView.text.toString() == response.name.toString()){
                holder.cityTemperature.text = response.main?.temp.toString()
            }
        }

    }

    override fun getItemCount(): Int {
        return cities.size
    }
}