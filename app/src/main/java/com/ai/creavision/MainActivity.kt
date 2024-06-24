package com.ai.creavision

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.adapty.Adapty
import com.adapty.models.AdaptyConfig
import com.adapty.utils.AdaptyResult
import com.ai.creavision.presentation.home.CreaVisionFragmentFactory
import com.ai.creavision.utils.DataHolder
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: CreaVisionFragmentFactory

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)


        initPaywall()
        checkPremium()

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)


        // Hide bottom nav on screens which don't require it
        lifecycleScope.launch {
            withStarted {
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {

                        R.id.homeFragment, R.id.yourArtsFragment -> bottomNavigationView.visibility =
                            View.VISIBLE

                        else -> bottomNavigationView.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun checkPremium() {

        Adapty.getProfile { result ->
            when (result) {
                is AdaptyResult.Success -> {
                    val profile = result.value

                    if (profile.accessLevels["premium"]?.isActive == true) {
                        DataHolder.isPremium = true
                        println("premium")
                    }
                }
                is AdaptyResult.Error -> {
                    val error = result.error
                    // handle the error
                }
            }
        }
    }


    private fun initPaywall() {

        Adapty.activate(
            applicationContext,
            AdaptyConfig.Builder("public_live_ZurMY5sb.E9iUhWnMgf0fnzmiRQck")
                .withObserverMode(false) //default false
                .withIpAddressCollectionDisabled(false) //default false
                .build()
        )

        println("brother")
        Adapty.getPaywall("placementId") { paywallResult ->
            when (paywallResult) {
                is AdaptyResult.Success -> {
                    val paywall = paywallResult.value
                    Adapty.getPaywallProducts(paywall) { productResult ->

                        when (productResult) {
                            is AdaptyResult.Success -> {

                                DataHolder.paywall = paywallResult.value

                            }

                            is AdaptyResult.Error -> {

                                println(productResult.error.toString())

                            }
                        }
                    }
                }

                is AdaptyResult.Error -> {
                    println(paywallResult.error.toString())

                }
            }
        }
    }
}

