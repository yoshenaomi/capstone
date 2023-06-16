package com.dicoding.capstone.ui.bottomnav

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.capstone.R
import com.dicoding.capstone.data.local.PrefDataStore
import com.dicoding.capstone.data.local.dataStore
import com.dicoding.capstone.databinding.ActivityBottomNavbarBinding
import com.dicoding.capstone.ui.login.LoginActivity
import com.dicoding.capstone.ui.login.LoginViewModel
import com.dicoding.capstone.utils.ViewModelFactory

class BottomNavbarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavbarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_navbar)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_detection
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }
}