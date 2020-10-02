package io.fajarca.buzznews

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val navController by lazy { Navigation.findNavController(this, R.id.navHostFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(navigationListener)
    }

    private val navigationListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when(destination.id) {
            R.id.fragmentWebBrowser -> hideBottomNavigation()
            else -> showBottomNavigation()
        }
    }

    private fun showBottomNavigation() {
        bottomNavigationView.visibility = View.VISIBLE
    }
    private fun hideBottomNavigation() {
        bottomNavigationView.visibility = View.GONE
    }
}
