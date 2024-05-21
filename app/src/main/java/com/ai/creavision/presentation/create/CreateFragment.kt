package com.ai.creavision.presentation.create

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentCreateBinding
import com.ai.creavision.domain.model.Input
import com.ai.creavision.domain.model.PromptRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CreateViewModel


    var prompt : String = ""

    var imgUrl : String = ""

    lateinit var bitmap : Bitmap


    private val appPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if (result.all { it.value }) {

            }else{
                Toast.makeText(requireContext(), "Permisson needed!", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private fun permissionNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED){
                Snackbar.make(requireView(), "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                    View.OnClickListener {
                        appPermissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))
                        appPermissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_VIDEO))
                    }).show()


            }
            else {//Eğer daha önce sorulmamışsa yapılacak işlemler
                appPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }
        }

        else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                Snackbar.make(requireView(), "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                    View.OnClickListener {
                        appPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                    }).show()


            }
            else {//Eğer daha önce sorulmamışsa yapılacak işlemler
                appPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }

        }
    }

    private fun saveBitmapToCache(bitmap: Bitmap): Uri {
        val cachePath = File(requireContext().cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "shared_image.png")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()
        return FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.fileprovider", file)
    }


    private fun shareImage(bitmap: Bitmap) {
        val imageUri = saveBitmapToCache(bitmap)
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/png"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Paylaş"))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments.let {
            prompt = it?.getString("prompt").toString()!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CreateViewModel::class.java)
        onClick()

        binding.animationView.visibility = View.VISIBLE
        binding.imageView.setImageDrawable(null)
        binding.animationView.playAnimation()
        viewModel.liveData.value = null

        observeLiveData()

        viewModel.createImage(PromptRequest(input = Input(prompt = prompt)))

    }

    private fun observeLiveData() {

        viewModel.liveData.observe(viewLifecycleOwner, Observer {  imageResponse ->


            Toast.makeText(requireContext(), "Geldi", Toast.LENGTH_LONG)
                .show()
            imageResponse?.let {


                imgUrl = imageResponse.imageUrl[0]


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
                            //binding.progressBar.visibility = View.GONE
                            binding.animationView.pauseAnimation()
                            binding.animationView.visibility = View.GONE
                            binding.txtGenerating.visibility = View.GONE

                            bitmap = (resource as BitmapDrawable).bitmap

                            println(imgUrl)


                            return false
                        }

                    })
                    //.placeholder(R.drawable.loading_spinner)
                    .into(binding.imageView)

            }
        }
        )
    }



    private fun onClick() {

        binding.btnShare.setOnClickListener() {
            val pulsateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.scale)
            binding.btnShare.startAnimation(pulsateAnimation)
            shareImage(bitmap)
        }

        binding.btnBack.setOnClickListener() {

            findNavController().popBackStack()

        }
    }
}