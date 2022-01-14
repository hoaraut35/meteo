package com.hoarauthomas.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hoarauthomas.weather.R
import com.hoarauthomas.weather.databinding.ActivityMainBinding
import com.hoarauthomas.weather.ui.list.CitiesFragment
import com.hoarauthomas.weather.ui.other.GithubFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
                R.id.page_2 -> {
                    openFrag(2)
                    true
                }
                else -> false
            }

        }

    }

    private fun openFrag(i: Int) {

        val ft = supportFragmentManager.beginTransaction()

        when (i) {
            1 -> ft.replace(R.id.mainFragmentContainer, CitiesFragment.newInstance("t", "y"))
            2 -> ft.replace(R.id.mainFragmentContainer, GithubFragment.newInstance("t", "y"))
        }

        ft.commit()

    }

}