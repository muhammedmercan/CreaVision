package com.ai.creavision.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentHomeBinding
import com.ai.creavision.domain.model.ArtStyleBottomSheetModel
import com.ai.creavision.domain.model.AspectRatioBottomSheetModel
import com.ai.creavision.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment @Inject constructor(
    val homeAdapter: HomeAdapter,
    val artStyleAdapter: ArtStyleAdapter

): Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val aspectRatioModalBottomSheet = AspectRatioBottomSheetModel()
    private val artStyleModalBottomSheet = ArtStyleBottomSheetModel(artStyleAdapter)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()

        binding.recyclerViewHome.adapter = homeAdapter

        homeAdapter.artyStyleResponseList = Constants.ART_STYLES


    }

    private fun onClick() {

        binding.cardViewAspectRatio.setOnClickListener() {

            aspectRatioModalBottomSheet.show(parentFragmentManager, AspectRatioBottomSheetModel.TAG)
        }

        binding.btnSeeAll.setOnClickListener() {

            artStyleModalBottomSheet.show(parentFragmentManager, ArtStyleBottomSheetModel.TAG)
        }

        binding.btnClearText.setOnClickListener() {

            binding.editTextPrompt.text.clear()
        }

        binding.btnSupriseMe.setOnClickListener() {
            val random = (0..100).random()
            binding.editTextPrompt.setText(Constants.PROMPTS[random])
        }

        artStyleAdapter.setOnItemClickListener {
            binding.recyclerViewHome.adapter?.notifyDataSetChanged()
        }



        binding.btnGenerate.setOnClickListener() {

            val prompt = binding.editTextPrompt.text.toString()
            val args = Bundle()
            args.putString("prompt", prompt)
            findNavController().navigate(R.id.action_homeFragment_to_createFragment,args)
        }
    }
}