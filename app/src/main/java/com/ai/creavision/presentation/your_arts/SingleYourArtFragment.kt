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
import com.ai.creavision.databinding.FragmentSingleYourArtBinding
import com.ai.creavision.domain.model.Favorite
import com.ai.creavision.presentation.BaseFragment
import com.ai.creavision.presentation.your_arts.SingleYourArtViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class SingleYourArtFragment @Inject constructor() : BaseFragment() {

    private lateinit var viewModel: SingleYourArtViewModel


    private var _binding: FragmentSingleYourArtBinding? = null
    private val binding get() = _binding!!
    lateinit var bitmap: Bitmap
    lateinit var favorite: Favorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            favorite = it?.getSerializable("favorite")!! as Favorite

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
        viewModel = ViewModelProvider(requireActivity()).get(SingleYourArtViewModel::class.java)


        init()
        onClick()
        observeLiveData()
    }

    private fun init() {

        viewModel.isFavoriteExists(favorite.imgUrl)


        //Picasso.get().load(favorite.imgUrl).into(binding.imageView);
        //Picasso.get().load(favorite.imgUrl).into(binding.imageView);


        Picasso.get()
            .load(favorite.imgUrl)
            //.skipMemoryCache() // Picasso'da doğrudan bellek önbelleğini atlama özelliği yok
            .into(object : com.squareup.picasso.Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                    // Resim yüklendiğinde yapılacak işlemler
                    this@SingleYourArtFragment.bitmap = bitmap
                    binding.imageView.setImageBitmap(bitmap)
                    binding.btnShare.isClickable = true
                    binding.btnShare.isEnabled = true
                    binding.btnDownload.isClickable = true
                    binding.btnDownload.isEnabled = true

                    binding.btnShare.alpha = 1F
                    binding.btnDownload.alpha = 1F
                }

                override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
                    // Resim yükleme başarısız olduğunda yapılacak işlemler
                    // Örneğin, hata mesajı göstermek
                    e.printStackTrace()
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    // Resim yüklenmeden önce yapılacak işlemler
                    // Örneğin, bir yükleniyor animasyonu göstermek
                }
            })


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

            viewModel.isFavoriteExists(favorite.imgUrl)

        })

        viewModel.liveDataDeleteResult.observe(viewLifecycleOwner, Observer { response ->

            viewModel.isFavoriteExists(favorite.imgUrl)
        })
    }

    private fun onClick() {

        binding.btnFavorite.setOnClickListener() {

            if (viewModel.liveDataIsExists.value!!) {
                //binding.btnfavorite.setImageResource(R.drawable.favorite_icon)
                viewModel.deleteFavorite(favorite.imgUrl)

            } else {
                viewModel.addFavorite(Favorite(favorite.imgUrl, favorite.prompt))
                //binding.btnfavorite.setImageResource(R.drawable.favorite_icon_fill)

            }
        }

        binding.btnShare.setOnClickListener() {
            val pulsateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.scale)
            binding.btnShare.startAnimation(pulsateAnimation)
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