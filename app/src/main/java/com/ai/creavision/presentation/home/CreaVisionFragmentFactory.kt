package com.ai.creavision.presentation.home

import FullScreenDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.ai.creavision.presentation.results.AllResultsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class CreaVisionFragmentFactory @Inject constructor(

) : FragmentFactory() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            HomeFragment::class.java.name -> HomeFragment(HomeAdapter(), ArtStyleAdapter())
            AllResultsFragment::class.java.name -> AllResultsFragment()
            FullScreenDialogFragment::class.java.name -> FullScreenDialogFragment()

            else -> super.instantiate(classLoader, className)
        }
    }
}