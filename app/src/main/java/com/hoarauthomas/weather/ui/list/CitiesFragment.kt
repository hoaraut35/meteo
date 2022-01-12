package com.hoarauthomas.weather.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoarauthomas.weather.R
import com.hoarauthomas.weather.databinding.FragmentCitiesBinding
import com.hoarauthomas.weather.models.City
import com.hoarauthomas.weather.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class CitiesFragment : Fragment() {


    private val listCities: MainViewModel by viewModels()

    private lateinit var binding: FragmentCitiesBinding

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCitiesBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        listCities.getCities().observe(viewLifecycleOwner) { listCities ->
            if (listCities.isNotEmpty()) {
                setupRecyclerView(recyclerView, listCities)
            }
        }





        return view

    }

    private fun setupRecyclerView(recyclerView: RecyclerView, listCities: List<City>) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val list = listOf<City>(City(name = "aa"), City(name = "bb"))
        recyclerView.adapter = CitiesAdapter(listCities)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CitiesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}