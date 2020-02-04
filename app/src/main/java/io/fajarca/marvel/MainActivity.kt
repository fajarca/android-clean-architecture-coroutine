package io.fajarca.marvel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavigation, navHostFragment.findNavController())
    }
}
