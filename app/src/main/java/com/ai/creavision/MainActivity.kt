package com.ai.creavision

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.adapty.Adapty
import com.adapty.models.AdaptyConfig
import com.adapty.utils.AdaptyResult
import com.ai.creavision.utils.Constants
import com.ai.creavision.utils.Constants.REPLICATE_API_KEY
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

        Adapty.activate(
            applicationContext,
            AdaptyConfig.Builder(Constants.ADAPTY_API_KEY)
                .withObserverMode(false) //default false
                .withIpAddressCollectionDisabled(false) //default false
                .build()
        )

        Adapty.getProfile { result ->
            when (result) {
                is AdaptyResult.Success -> {
                    val profile = result.value

                    if (profile.accessLevels["premium"]?.isActive == true) {
                        DataHolder.isPremium.value = true
                        println("premium")
                    } else {
                        initPaywall()
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
        Adapty.getPaywall("placementId") { paywallResult ->
            when (paywallResult) {
                is AdaptyResult.Success -> {
                    val paywall = paywallResult.value
                    Adapty.getPaywallProducts(paywall) { productResult ->

                        when (productResult) {
                            is AdaptyResult.Success -> {
                                DataHolder.paywall.value = paywallResult.value
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

