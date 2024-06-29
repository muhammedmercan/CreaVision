package com.ai.creavision

import FullScreenDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.ai.creavision.presentation.home.ArtStyleAdapter
import com.ai.creavision.presentation.home.HomeAdapter
import com.ai.creavision.presentation.home.HomeFragment
import com.ai.creavision.presentation.your_arts.YourArtsAdapter
import com.ai.creavision.presentation.your_arts.YourArtsFragment
import com.ai.creavision.presentation.results.AllResultsFragment
import javax.inject.Inject

class CreaVisionFragmentFactory @Inject constructor(
    private val yourArtsAdapter: YourArtsAdapter,
    private val homeAdapter: HomeAdapter,
    private val artStyleAdapter: ArtStyleAdapter

) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            HomeFragment::class.java.name -> HomeFragment()
            AllResultsFragment::class.java.name -> AllResultsFragment()
            FullScreenDialogFragment::class.java.name -> FullScreenDialogFragment()
            YourArtsFragment::class.java.name -> YourArtsFragment(yourArtsAdapter)

            else -> super.instantiate(classLoader, className)
        }
    }
}