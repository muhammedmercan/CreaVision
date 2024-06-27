package com.ai.creavision.presentation.premium

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentAlreadyPremiumBinding
import com.ai.creavision.databinding.FragmentHomeBinding


class AlreadyPremiumFragment : Fragment() {

    private var _binding: FragmentAlreadyPremiumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlreadyPremiumBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
    }

    private fun onClick() {

        binding.btnBack.setOnClickListener() {
            findNavController().popBackStack()
        }

        binding.btnCancelSubscribe.setOnClickListener() {
            val playStoreUrl = "https://play.google.com/store/account/subscriptions"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl))
            startActivity(intent)
        }
    }
}