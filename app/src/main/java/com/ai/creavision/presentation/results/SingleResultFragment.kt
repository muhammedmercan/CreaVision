package com.ai.creavision.presentation.results

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentSingleResultBinding
import com.ai.creavision.domain.model.Favorite
import com.ai.creavision.presentation.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.io.File
import javax.inject.Inject


class SingleResultFragment @Inject constructor() : BaseFragment() {

    private lateinit var viewModel: SingleResultViewModel

    private var _binding: FragmentSingleResultBinding? = null
    private val binding get() = _binding!!
    private var imgUrl = ""
    private var prompt = ""
    lateinit var bitmap: Bitmap
    var savedImagePath = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            imgUrl = it?.getString("imgUrl").toString()
            prompt = it?.getString("prompt").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSingleResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SingleResultViewModel::class.java)

        init()
        onClick()
        observeLiveData()
    }

    private fun init() {

        viewModel.isFavoriteExistsWithUrl(imgUrl)

        /*
                if (viewModel.isFavoriteExists(imgUrl)) {
                    binding.btnfavorite.setImageResource(R.drawable.favorite_icon_fill)
                }
         */

        Glide
            .with(this)
            .load(imgUrl)
            //.override(500,500)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {

                    bitmap = (resource as BitmapDrawable).bitmap

                    binding.btnShare.isClickable = true
                    binding.btnShare.isEnabled = true
                    binding.btnDownload.isClickable = true
                    binding.btnDownload.isEnabled = true

                    binding.btnShare.alpha = 1F
                    binding.btnDownload.alpha = 1F

                    return false
                }
            })
            .into(binding.imageView)
    }

    private fun observeLiveData() {

        viewModel.liveDataIsExists.observe(viewLifecycleOwner, Observer { imageResponse ->

            if (imageResponse) {
                binding.btnFavorite.setImageResource(R.drawable.favorite_icon_fill)

            } else {
                binding.btnFavorite.setImageResource(R.drawable.favorite_icon)

            }
        })

        viewModel.liveDataAddResult.observe(viewLifecycleOwner, Observer { response ->

            viewModel.isFavoriteExistsWithUrl(imgUrl)

        })

        viewModel.liveDataDeleteResult.observe(viewLifecycleOwner, Observer { response ->

            viewModel.isFavoriteExistsWithUrl(imgUrl)
        })
    }

    private fun onClick() {

        binding.btnFavorite.setOnClickListener() {

            if (viewModel.liveDataIsExists.value!!) {
                //binding.btnfavorite.setImageResource(R.drawable.favorite_icon)
                viewModel.deleteProductWithUrl(imgUrl)

            } else {
                savedImagePath = saveImageToInternalStorage(bitmap)!!
                viewModel.addFavorite(Favorite(imgUrl = imgUrl , imgPath = savedImagePath, prompt =  prompt))
                //binding.btnfavorite.setImageResource(R.drawable.favorite_icon_fill)

            }
        }

        binding.btnReport.setOnClickListener() {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Report")
                .setMessage("You can report the content to us if you find it NSFW, offensive or for any other reason. So you can help us enhance our service.")
                .setCancelable(false)
                .setPositiveButton("Report") { dialog, which ->
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Report")
                        .setMessage("Reported successfully.")
                        .setCancelable(false)
                        .setPositiveButton("Close") { dialog, which ->
                            dialog.dismiss()
                        }

                        .show()
                }
                .setNegativeButton("Cancel"){ dialog, which ->
                    dialog.dismiss()
                }
                .show()

        }

        binding.btnShare.setOnClickListener() {
            //val pulsateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.scale)
            //binding.btnShare.startAnimation(pulsateAnimation)
            shareImage(bitmap)
        }

        binding.btnDownload.setOnClickListener() {
            permissionNotification()
            if (allPermissionsGranted) {
                Snackbar.make(requireView(), R.string.image_downloading, Snackbar.LENGTH_SHORT)
                    .show()
                saveImage(bitmap)
            }
        }

        binding.btnBack.setOnClickListener() {
            findNavController().popBackStack()

        }
    }
}