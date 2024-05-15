package com.ai.creavision.presentation.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class CreaVisionFragmentFactory @Inject constructor(

) : FragmentFactory() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            HomeFragment::class.java.name -> HomeFragment(HomeAdapter())

            else -> super.instantiate(classLoader, className)
        }
    }
}