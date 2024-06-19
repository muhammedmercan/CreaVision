package com.ai.creavision.presentation.results

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentSingleResultBinding
import com.ai.creavision.databinding.FragmentSingleYourArtBinding
import com.ai.creavision.presentation.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SingleYourArtFragment : BaseFragment() {

    private var _binding: FragmentSingleYourArtBinding? = null
    private val binding get() = _binding!!
    lateinit var bitmap: Bitmap
    private var imgFile = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            imgFile = it?.getString("imgFile").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSingleYourArtBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        onClick()
    }

    private fun init() {

        Glide
            .with(this)
            .load(File(imgFile))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
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

    private fun onClick() {

        binding.btnShare.setOnClickListener() {
            val pulsateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.scale)
            binding.btnShare.startAnimation(pulsateAnimation)
            shareImage(bitmap)
        }

        binding.btnDownload.setOnClickListener() {
            permissionNotification()
            if (allPermissionsGranted) {
                Snackbar.make(requireView(), R.string.image_downloading, Snackbar.LENGTH_SHORT).show()
                saveImage(bitmap)
            }
        }

        binding.btnBack.setOnClickListener() {
            findNavController().popBackStack()
        }
    }
}