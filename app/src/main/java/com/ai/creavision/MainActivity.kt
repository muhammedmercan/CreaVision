package com.ai.creavision

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ai.creavision.databinding.ActivityMainBinding
import com.ai.creavision.presentation.home.CreaVisionFragmentFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: CreaVisionFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)

        var bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val navController =
            Navigation.findNavController(this@MainActivity, R.id.fragmentContainerView)
        bottomNav.setupWithNavController(navController)


        // Hide bottom nav on screens which don't require it
        lifecycleScope.launch {
            withStarted {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {

                    R.id.homeFragment, R.id.allOldResultsFragment, R.id.fullScreenDialogFragment -> bottomNav.visibility =
                        View.VISIBLE

                    else -> bottomNav.visibility = View.GONE
                }
                }
            }
        }
    }
}

