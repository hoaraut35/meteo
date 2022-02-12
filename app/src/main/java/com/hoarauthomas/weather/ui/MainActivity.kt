package com.hoarauthomas.weather.ui

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hoarauthomas.weather.R
import com.hoarauthomas.weather.databinding.ActivityMainBinding
import com.hoarauthomas.weather.ui.Player.PlayerFragment
import com.hoarauthomas.weather.ui.canon.CanonFragment
import com.hoarauthomas.weather.ui.listCities.CitiesFragment
import com.hoarauthomas.weather.ui.tchat.GithubFragment
import com.hoarauthomas.weather.ui.system.SystemFragment
import com.hoarauthomas.weather.ui.webview.WebViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding




    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    openFrag(1)
                    true
                }

                R.id.player -> {
                    openFrag(4)
                    true
                }

                R.id.firestore_page -> {
                    openFrag(2)
                    true
                }



                R.id.system_page -> {
                    openFrag(5)
                    true
                }

                else -> false
            }

        }

        val db = Firebase.firestore

        val user = hashMapOf("firstName" to "Ada",
        "lastName" to "Lovelace",
        "born" to 1815)

//        db.collection("utilisateurs").add(user).addOnSuccessListener {
//            Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener {
//            Toast.makeText(this,"Fail",Toast.LENGTH_SHORT).show()
//        }


        val connectivityManager =
            getSystemService(ConnectivityManager::class.java)

        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network : Network) {

                Log.i("TAG", "The default network is now: " + network)
            }

            override fun onLost(network : Network) {
                Log.i("TAG", "The application no longer has a default network. The last default network was " + network)
            }

            override fun onCapabilitiesChanged(network : Network, networkCapabilities : NetworkCapabilities) {
                Log.i("TAG","The default network changed capabilities: " + networkCapabilities)
            }

            override fun onLinkPropertiesChanged(network : Network, linkProperties : LinkProperties) {
                Log.i("TAG", "The default network changed link properties: " + linkProperties)
            }
        })

    }

    private fun openFrag(i: Int) {

        val ft = supportFragmentManager.beginTransaction()

        when (i) {
            1 -> ft.replace(R.id.mainFragmentContainer, CitiesFragment.newInstance("t", "y"))
            2 -> ft.replace(R.id.mainFragmentContainer, GithubFragment.newInstance("t", "y"))
            3 -> ft.replace(R.id.mainFragmentContainer, SystemFragment.newInstance("tt","tt"))
            4 -> ft.replace(R.id.mainFragmentContainer, PlayerFragment.newInstance("tt","tt"))
            5 -> ft.replace(R.id.mainFragmentContainer, WebViewFragment.newInstance("",""))

        }

        ft.commit()

    }

}