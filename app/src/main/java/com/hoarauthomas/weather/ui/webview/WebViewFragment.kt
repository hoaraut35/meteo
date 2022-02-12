package com.hoarauthomas.weather.ui.webview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hoarauthomas.weather.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebViewBinding
    private val viewModelWebView: WebViewModel by viewModels()
    private lateinit var  aSpinnerResult :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        val view = binding.root


        val spinner = binding.spinner
        val webView = binding.webView


        viewModelWebView.getCitiesRoom().observe(viewLifecycleOwner) { cities ->
            if (!cities.isNullOrEmpty()) {
                val listString = mutableListOf<String>()
                cities.forEach { city ->
                    listString.add(city.name.toString())
                }
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listString)
                spinner.adapter = adapter
            }
        }

        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    aSpinnerResult =
                        parent?.getItemAtPosition(position).toString()

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        binding.button.setOnClickListener {
            Log.i("[CLICK]", "set on click" + aSpinnerResult)

            //open webview


            webView.webViewClient = WebViewClient()
            webView.loadUrl("http://www.google.fr/search?q=" + aSpinnerResult + "+" + "meteo")

        }


        return view
    }




    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WebViewFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }

}