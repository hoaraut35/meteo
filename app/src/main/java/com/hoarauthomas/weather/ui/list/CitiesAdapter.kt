package com.hoarauthomas.weather.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hoarauthomas.weather.databinding.ItemCityBinding
import com.hoarauthomas.weather.models.City

class CitiesAdapter(private val cities: List<City>) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        var cityNameView = binding.cityNameView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cityNameView.text = cities[position].name
        holder.cityNameView.tag = cities[position].id
    }

    override fun getItemCount(): Int {
        return cities.size
    }
}