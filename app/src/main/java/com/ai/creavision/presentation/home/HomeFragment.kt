package com.ai.creavision.presentation.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentHomeBinding
import com.ai.creavision.domain.model.ArtStyleBottomSheetModel
import com.ai.creavision.domain.model.Style
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

        binding.editTextPrompt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val inputText = s.toString()

                if (inputText.isEmpty()) {
                    binding.btnGenerate.alpha = 0.5F
                    binding.btnGenerate.isClickable = false
                } else {
                    binding.btnGenerate.alpha = 1F
                    binding.btnGenerate.isClickable = true

                }
            }
        })
    }


    private fun onClick() {


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

                var width = 1024
                var height = 1024

                when(binding.chipGroupAspectRatio.checkedChipId) {
                    binding.chip11.id -> {width = 1024; height = 1024 }
                    binding.chip916.id -> {width = 768; height = 1360 }
                    binding.chip169.id -> {width = 1360; height = 768 }
                    binding.chip43.id -> {width = 1152; height = 864 }
                    binding.chip34.id -> {width = 864; height = 1152 }
                    binding.chip23.id -> {width = 768; height = 1152 }
                    binding.chip32.id -> {width = 1152; height = 768 }

                    else -> {width = 1024; height = 1024 }
                }

                var prompt = binding.editTextPrompt.text.toString()

                if (!Style.getValue().isNullOrEmpty()) {
                    prompt += ", style = "  + Style.getValue()
                }

                val args = Bundle()
                args.putString("prompt", prompt)
                args.putInt("width", width)
                args.putInt("height", height)
                findNavController().navigate(R.id.action_homeFragment_to_createFragment,args)
            }
        }
    }
