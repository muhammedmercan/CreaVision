package com.ai.creavision.presentation.results


import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentAllResultsBinding
import com.ai.creavision.domain.model.Input
import com.ai.creavision.domain.model.PromptRequest
import com.ai.creavision.utils.DataHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllResultsFragment : Fragment() {

    private var _binding: FragmentAllResultsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AllResultsViewModel

    var prompt: String = ""
    var negativePrompt: String = ""
    var width: Int = 0
    var height: Int = 0

    var numOutput = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            prompt = it?.getString("prompt").toString()!!
            negativePrompt = it?.getString("negativePrompt").toString()!!
            width = it?.getInt("width")!!
            height = it?.getInt("height")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllResultsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AllResultsViewModel::class.java)


        onClick()

        if (viewModel.liveData.value == null) {

            if (DataHolder.isPremium.value!!) {
                numOutput = 4
            }
            viewModel.createImage(
                PromptRequest(
                    input = Input(
                        width = width,
                        height = height,
                        prompt = prompt,
                        numOutputs = numOutput,
                        negativePrompt = negativePrompt
                    )
                )
            )
        }
        observeLiveData()
    }


    private fun observeLiveData() {

        viewModel.liveData.observe(viewLifecycleOwner, Observer { imageResponse ->

            imageResponse?.let {

                if (!imageResponse.error.isNullOrEmpty() || imageResponse.logs.contains("NSFW")) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Error")
                        .setMessage(it.error)
                        .setMessage("NSFW content detected. Try running it again, or try a different prompt.")
                        .setPositiveButton("Understand") { dialog, which ->
                            dialog.dismiss()
                            findNavController().popBackStack()

                        }
                        .show()
                } else {
                    DataHolder.currentGlideCache = false

                    if (DataHolder.isPremium.value!!) {
                        setImageViewClickListener(binding.imageView, imageResponse.imageUrl[0])
                        setImageViewClickListener(binding.imageView2, imageResponse.imageUrl[1])
                        setImageViewClickListener(binding.imageView3, imageResponse.imageUrl[2])
                        setImageViewClickListener(binding.imageView4, imageResponse.imageUrl[3])
                        loadAndHandleImagePremium(binding.imageView, imageResponse.imageUrl[0])
                        loadAndHandleImagePremium(binding.imageView2, imageResponse.imageUrl[1])
                        loadAndHandleImagePremium(binding.imageView3, imageResponse.imageUrl[2])
                        loadAndHandleImagePremium(binding.imageView4, imageResponse.imageUrl[3])
                        resetImage(binding.imageView2)
                        resetImage(binding.imageView3)
                        resetImage(binding.imageView4)

                    } else {

                        setImageViewClickListener(binding.imageView, imageResponse.imageUrl[0])
                        setImageViewClickListener(binding.imageView2, imageResponse.imageUrl[0])
                        setImageViewClickListener(binding.imageView3, imageResponse.imageUrl[0])
                        setImageViewClickListener(binding.imageView4, imageResponse.imageUrl[0])
                        loadAndHandleImagePremium(binding.imageView, imageResponse.imageUrl[0])
                        loadAndHandleImage(binding.imageView2, imageResponse.imageUrl[0], binding.imgLock, binding.txtLock)
                        loadAndHandleImage(binding.imageView3, imageResponse.imageUrl[0], binding.imgLock2, binding.txtLock2)
                        loadAndHandleImage(binding.imageView4, imageResponse.imageUrl[0], binding.imgLock3, binding.txtLock3)
                    }
                }
            }
        }
        )
    }

    private fun resetImage(imageView: ImageView) {
        imageView.alpha = 1F
        imageView.imageTintList = null
    }


    private fun loadAndHandleImage(imageView: ImageView, imageUrl: String, imgLock: ImageView, txtLock:TextView) {
        Glide.with(this)
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    // Handle failed loading
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    // Handle successful loading
                    binding.animationView.pauseAnimation()
                    binding.animationView.visibility = View.GONE

                    imgLock.visibility = View.VISIBLE
                    txtLock.visibility = View.VISIBLE
                    return false
                }
            })
            .into(imageView)
    }

    private fun loadAndHandleImagePremium(imageView: ImageView, imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    // Handle failed loading
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    // Handle successful loading
                    binding.animationView.pauseAnimation()
                    binding.animationView.visibility = View.GONE
                    return false
                }
            })
            .into(imageView)
    }


    private fun setImageViewClickListener(imageView: ImageView, imageUrl: String) {

        if (DataHolder.isPremium.value!! || imageView == binding.imageView) {
            imageView.setOnClickListener {
                val args = Bundle().apply {
                    putString("imgUrl", imageUrl)
                    putString("prompt", prompt)
                }
                findNavController().navigate(
                    R.id.action_allResultsFragment_to_singleResultFragment,
                    args
                )
            }
        }
        else {
            imageView.setOnClickListener {
                findNavController().navigate(R.id.action_allResultsFragment_to_paywallUiFragment)
            }

        }
    }

    private fun onClick() {

        binding.btnBack.setOnClickListener() {
            findNavController().popBackStack()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        resetUI()

    }

    private fun resetUI() {
        binding.animationView.visibility = View.VISIBLE
        binding.imageView.setImageDrawable(null)
        binding.animationView.playAnimation()
        viewModel.reset()
    }
}