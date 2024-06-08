package com.ai.creavision.presentation.old_results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ai.creavision.databinding.FragmentYourArtsBinding
import com.bumptech.glide.Glide
import javax.inject.Inject


class YourArtsFragment @Inject constructor(
    val yourArtsAdapter: YourArtsAdapter

) : Fragment() {


    private var _binding: FragmentYourArtsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentYourArtsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        onClick()

        binding.recyclerViewYourArts.adapter = yourArtsAdapter
        binding.recyclerViewYourArts.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)


        val photoCacheDir = Glide.getPhotoCacheDir(requireContext())

        val photoFiles = photoCacheDir?.listFiles()?.toList()

        println("asdsad" + photoFiles?.size)


        yourArtsAdapter.photoFiles = photoFiles

    }

    private fun onClick() {


    }
}