package com.hoarauthomas.weather.ui.tchat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.hoarauthomas.weather.R
import com.hoarauthomas.weather.databinding.FragmentCitiesBinding
import com.hoarauthomas.weather.databinding.FragmentGithubBinding
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class GithubFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentGithubBinding

    private val viewModelChat: ChatViewModel by viewModels()

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


        binding = FragmentGithubBinding.inflate(inflater, container, false)
        val view = binding.root




         viewModelChat.getAllMessageForChat("android").addSnapshotListener { value, error ->

            if (value!!.isEmpty){
                Log.i("[SQL]","query null")
            }
             else{
                Log.i("[SQL]","query "+ value.documents.toString() )
            }
        }


        return view






    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GithubFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}