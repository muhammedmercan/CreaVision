package com.ai.creavision.presentation.results


import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentAllResultsBinding
import com.ai.creavision.domain.model.Input
import com.ai.creavision.domain.model.PromptRequest
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
    lateinit var viewModel: AllResultsViewModel


    var prompt: String = ""
    var negativePrompt: String = ""
    var width: Int = 0
    var height: Int = 0


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
            viewModel.createImage(
                PromptRequest(
                    input = Input(
                        width = width,
                        height = height,
                        prompt = prompt,
                        negativePrompt = negativePrompt
                    )
                )
            )
        }
        observeLiveData()
    }


    private fun observeLiveData() {

        viewModel.liveData.observe(viewLifecycleOwner, Observer { imageResponse ->

            Toast.makeText(requireContext(), "Geldi", Toast.LENGTH_LONG)
                .show()

            imageResponse?.let {

                if (!imageResponse.error.isNullOrEmpty()) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Error")
                        .setMessage(it.error)
                        .setPositiveButton("Understand") { dialog, which ->
                            dialog.dismiss()
                            findNavController().popBackStack()

                        }
                        .show()
                } else {

                    setImageViewClickListener(binding.imageView, imageResponse.imageUrl[0])
                    setImageViewClickListener(binding.imageView2, imageResponse.imageUrl[1])
                    setImageViewClickListener(binding.imageView3, imageResponse.imageUrl[2])
                    setImageViewClickListener(binding.imageView4, imageResponse.imageUrl[3])

                    loadAndHandleImage(binding.imageView, imageResponse.imageUrl[0])
                    loadAndHandleImage(binding.imageView2, imageResponse.imageUrl[1])
                    loadAndHandleImage(binding.imageView3, imageResponse.imageUrl[2])
                    loadAndHandleImage(binding.imageView4, imageResponse.imageUrl[3])


                }
            }
        }
        )
    }

    private fun loadAndHandleImage(imageView: ImageView, imageUrl: String) {
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
        imageView.setOnClickListener {
            val args = Bundle().apply {
                putString("imgUrl", imageUrl)
            }
            findNavController().navigate(R.id.action_createFragment_to_singleResultFragment, args)
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