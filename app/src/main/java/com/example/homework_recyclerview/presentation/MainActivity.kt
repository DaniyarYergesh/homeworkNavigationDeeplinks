package com.example.homework_recyclerview.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.convertor.R
import com.example.convertor.databinding.ActivityMainBinding
import com.example.homework_recyclerview.presentation.fragments.converter.ConvertorFragment
import com.example.homework_recyclerview.presentation.fragments.personal_page.PinCode
import com.example.homework_recyclerview.presentation.fragments.movies.Movies
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.itemIconTintList = null

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navigationController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navigationController)
    }
}