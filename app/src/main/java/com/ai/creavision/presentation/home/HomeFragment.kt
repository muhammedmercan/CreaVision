package com.ai.creavision.presentation.home

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentHomeBinding
import com.ai.creavision.domain.model.ArtStyleBottomSheetModel
import com.ai.creavision.domain.model.PremiumBottomSheetModel
import com.ai.creavision.domain.model.Style
import com.ai.creavision.utils.Constants
import com.ai.creavision.utils.DataHolder
import com.ai.creavision.utils.NetworkUtils
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject



@AndroidEntryPoint
class HomeFragment: Fragment() {

    @Inject
    lateinit  var homeAdapter: HomeAdapter

    @Inject
    lateinit  var artStyleAdapter: ArtStyleAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val artStyleModalBottomSheet = ArtStyleBottomSheetModel(ArtStyleAdapter())

    private var rewardedAd: RewardedAd? = null
    private final var TAG = "MainActivity"

    private var isLoading = false

    var count = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private fun checkInternetConnection(): Boolean {

        if (!NetworkUtils.isOnline(requireContext())) {

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Error")
                .setMessage("No Internet Connection")
                .setCancelable(false)
                .setPositiveButton("Try Again") { dialog, which ->
                    dialog.dismiss()
                    checkInternetConnection()

                }
                .show()
            return false
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInternetConnection()
        onClick()
        loadRewardedAd()
        observeLiveData()


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
                    binding.btnGenerate.isEnabled = false
                } else {
                    binding.btnGenerate.alpha = 1F
                    binding.btnGenerate.isClickable = true
                    binding.btnGenerate.isEnabled = true

                }
            }
        })
    }

    private fun observeLiveData() {

        DataHolder.paywall.observe(viewLifecycleOwner, Observer {

            if (count == 0) {
                findNavController().navigate(R.id.action_homeFragment_to_paywallUiFragment)
                count++
            }
            if (DataHolder.paywall.value != null) {
                binding.btnPremium.visibility = View.VISIBLE
            }
        })

        DataHolder.isPremium.observe(viewLifecycleOwner, Observer {

            if (it!!) {
                binding.btnPremium.visibility = View.VISIBLE
            }
        })
    }

    private fun onClick() {

        binding.btnPremium.setOnClickListener() {

            if (DataHolder.paywall != null && !DataHolder.isPremium.value!!) {
                println(DataHolder.paywall)
                println(DataHolder.isPremium)
                findNavController().navigate(R.id.action_homeFragment_to_paywallUiFragment)
            }

            if (DataHolder.isPremium.value!!) {
                findNavController().navigate(R.id.action_homeFragment_to_alreadyPremiumFragment)

            }
        }

        binding.btnSeeAll.setOnClickListener() {

            artStyleModalBottomSheet.show(parentFragmentManager, ArtStyleBottomSheetModel.TAG)
        }

        binding.btnClearText.setOnClickListener() {

            binding.editTextPrompt.text.clear()
        }

        binding.btnSupriseMe.setOnClickListener() {
            val random = (0..100).random()
            binding.editTextPrompt.setText(Constants.RANDOM_PROMPTS[random])
        }

        artStyleAdapter.setOnItemClickListener {
            binding.recyclerViewHome.adapter?.notifyDataSetChanged()
        }

        binding.btnGenerate.setOnClickListener() {
            if (checkInternetConnection()) {
                if (DataHolder.isPremium.value!!) {
                    if (rewardedAd != null) {
                        showRewardedAd()
                    } else {
                        Snackbar.make(requireView(), "Error Load Ads", Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    goCreate()
                }
            }
        }
    }

    private fun goCreate() {

        var width = 1024
        var height = 1024

        when (binding.chipGroupAspectRatio.checkedChipId) {
            binding.chip11.id -> {
                width = 1024; height = 1024
            }

            binding.chip916.id -> {
                width = 768; height = 1360
            }

            binding.chip169.id -> {
                width = 1360; height = 768
            }

            binding.chip43.id -> {
                width = 1152; height = 864
            }

            binding.chip34.id -> {
                width = 864; height = 1152
            }

            binding.chip23.id -> {
                width = 768; height = 1152
            }

            binding.chip32.id -> {
                width = 1152; height = 768
            }
        }

        var prompt = binding.editTextPrompt.text.toString()
        var negativePrompt = ""

        if (!Style.getValue().isNullOrEmpty()) {
            var resultPrompt = Constants.PROMPTS.get(Style.getValue())?.prompt.toString()
            resultPrompt = resultPrompt?.replace("{prompt}", prompt).toString()
            prompt = resultPrompt
            negativePrompt = Constants.PROMPTS.get(Style.getValue())?.negativePrompt.toString()
        }

        val args = Bundle()
        args.putString("prompt", prompt)
        args.putString("negativePrompt", negativePrompt)
        args.putInt("width", width)
        args.putInt("height", height)
        findNavController().navigate(R.id.action_homeFragment_to_allResultsFragment, args)

    }

    private fun loadRewardedAd() {
        if (rewardedAd == null && !isLoading) {
            isLoading = true
            val adRequest = AdRequest.Builder().build()

            RewardedAd.load(
                requireContext(),
                Constants.REWARDED_AD,
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.toString())
                        rewardedAd = null
                        isLoading = false
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        Log.d(TAG, "Ad was loaded.")
                        rewardedAd = ad
                        isLoading = false
                    }
                }
            )
        }
    }

    private fun showRewardedAd() {

        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                rewardedAd = null
                loadRewardedAd()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                rewardedAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
        rewardedAd?.show(requireActivity()) { rewardItem: RewardItem ->
            // Handle the reward.
            val rewardAmount = rewardItem.amount
            val rewardType = rewardItem.type
            // Reward the user.
            goCreate()
        }
    }

    override fun onDestroyView() {
        binding.recyclerViewHome.setAdapter(null);
        _binding = null
        rewardedAd = null
        isLoading = false
        super.onDestroyView()
        // View ile ilgili kaynakları serbest bırakmak için gerekli işlemler

    }
}
