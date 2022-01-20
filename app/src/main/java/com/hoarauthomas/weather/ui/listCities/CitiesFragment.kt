package com.hoarauthomas.weather.ui.listCities


import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hoarauthomas.weather.R
import com.hoarauthomas.weather.databinding.FragmentCitiesBinding
import com.hoarauthomas.weather.models.City
import com.hoarauthomas.weather.ui.detailcity.CityDetails
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class CitiesFragment : Fragment() {

    private val viewModelCities: CitiesViewModel by viewModels()

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
    ): View {

        binding = FragmentCitiesBinding.inflate(inflater, container, false)
        val view = binding.root

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.isVisible = true

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)


        viewModelCities.getViewState().observe(viewLifecycleOwner){

        }


        viewModelCities.weatherLiveData().observe(viewLifecycleOwner) { cityData ->
            Log.i("[API]", "city :" + cityData.name.toString())

            //update firestore
            viewModelCities.createCityDetail(cityData,"${cityData.name}-${cityData.sys?.country}")
        }

        //get room data...
        viewModelCities.getCities().observe(viewLifecycleOwner) { listCities ->


            if (listCities != null) {

                //get firestore weather data for each city...
                listCities.forEach { city ->
                    viewModelCities.callCityDetail("${city.name}-${city.countryCode}")
                }

                viewModelCities.getCityDetail().observe(viewLifecycleOwner){myCityDetailList ->

                    myCityDetailList.forEach {
                        Log.i("[API]","show temp:" + it.main?.temp)
                        Toast.makeText(
                            (requireContext()),
                            "${it.main?.temp} in ${it.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                listCities.forEach { city ->
                    viewModelCities.getWeatherByCity(city.name.toString(), "fr")
                    viewModelCities.createCity(city)
                }

                setupRecyclerView(recyclerView, listCities)

                val simpleCallback = object :
                    ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                    ) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        Log.i("[TOUCH]", "touch")
                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                        if (direction == ItemTouchHelper.RIGHT) {
                            //swipe to the right
                            viewModelCities.deleteCity(
                                City(
                                    id = viewHolder.itemView.findViewById<TextView>(
                                        R.id.city_name_view
                                    ).tag as Int
                                )
                            )


                        }

                        if (direction == ItemTouchHelper.LEFT) {
                            openFrag(
                                1,
                                viewHolder.itemView.findViewById<TextView>(R.id.city_name_view).text.toString()
                            )
                        }


                    }

                    override fun onChildDraw(
                        c: Canvas,
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        dX: Float,
                        dY: Float,
                        actionState: Int,
                        isCurrentlyActive: Boolean
                    ) {
                        super.onChildDraw(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )



                        RecyclerViewSwipeDecorator.Builder(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )

                            .addBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.cardview_shadow_start_color
                                )
                            )

                            //open detail
                            .addSwipeLeftActionIcon(R.drawable.ic_baseline_view_list_24)
                            .addSwipeLeftBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.green
                                )
                            )

                            //delete
                            .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                            .addSwipeRightBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red
                                )
                            )
                            .create()
                            .decorate()


                    }

                }

                val itemTouchHelper = ItemTouchHelper(simpleCallback)
                itemTouchHelper.attachToRecyclerView(recyclerView)


            }

        }

        viewModelCities.cityInsertedLive().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "new city added ", Toast.LENGTH_LONG).show()
        }

        binding.cityAddBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Add new city")
                .setMessage("You must enter a new name to create a city ...")
                .setView(inflater.inflate(R.layout.city_add_dialog_layout, null))
                .setPositiveButton(resources.getString(R.string.city_add_text_btn)) { dialog, _ ->
                    val text =
                        (dialog as androidx.appcompat.app.AlertDialog).findViewById<EditText>(R.id.city_add_edit)?.text.toString()

                    viewModelCities.insertCity(City(name = text))


                }
                .setNegativeButton(resources.getString(R.string.city_cancel_text_btn)) { _, _ ->

                    Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        return view
    }


    private fun deleteConfirm(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Are you sure ?")
            .setMessage("You can delete this city,  must enter a new name to create a city ...")
            .setPositiveButton(resources.getString(R.string.city_add_text_btn)) { dialog, _ ->

            }
            .setNegativeButton(resources.getString(R.string.city_cancel_text_btn)) { _, _ ->
                Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
            }
            .show()

    }

    private fun setupRecyclerView(recyclerView: RecyclerView, listCities: List<City>) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = CitiesAdapter(listCities)
    }

    private fun openFrag(i: Int, city: String) {

        val ft = parentFragmentManager.beginTransaction()

        when (i) {
            1 -> ft.replace(
                R.id.mainFragmentContainer,
                CityDetails.newInstance(city, "y"),
                "detail"
            ).addToBackStack("detail")
        }




        ft.commit()

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