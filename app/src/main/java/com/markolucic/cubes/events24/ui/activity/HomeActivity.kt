package com.markolucic.cubes.events24.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.databinding.ActivityHomeBinding
import com.markolucic.cubes.events24.ui.fragment.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(HomeFragment.newInstance())
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {

            val selectedFragment: Fragment = when (it.itemId) {
                R.id.home -> HomeFragment.newInstance()
                R.id.search -> SearchFragment.newInstance()
                R.id.tickets -> TicketsFragment.newInstance()
                R.id.favorites -> FavoritesFragment.newInstance()
                R.id.profile -> ProfileFragment.newInstance()
                else -> HomeFragment.newInstance()
            }

            setFragment(selectedFragment)

            true
        }
    }

    private fun setFragment(selectedFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, selectedFragment)
            .commit()
    }
}