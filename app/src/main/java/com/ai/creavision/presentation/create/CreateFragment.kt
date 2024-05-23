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
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CreateViewModel


    var prompt : String = ""
    var width : Int = 0
    var height : Int = 0
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            prompt = it?.getString("prompt").toString()!!
            width = it?.getInt("width")!!
            height = it?.getInt("height")!!
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

        resetUI()
        onClick()
        viewModel.createImage(PromptRequest(input = Input(width = width, height = height , prompt = prompt)))

        observeLiveData()

    }

    private fun observeLiveData() {

        viewModel.liveData.observe(viewLifecycleOwner, Observer {  imageResponse ->

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
                    .into(binding.imageView)
            }
        }}
        )
    }

    private fun permissionNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES)) {
                    Snackbar.make(requireView(), "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                        View.OnClickListener {
                            appPermissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))
                        }).show()
                }
                else {//Eğer daha önce sorulmamışsa yapılacak işlemler
                    appPermissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))
                }
            }
        }

        else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Snackbar.make(requireView(), "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                        View.OnClickListener {
                            appPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        }).show()
                }
                else {//Eğer daha önce sorulmamışsa yapılacak işlemler
                    appPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE))
                }
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


    private fun onClick() {

        binding.btnShare.setOnClickListener() {
            val pulsateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.scale)
            binding.btnShare.startAnimation(pulsateAnimation)
            shareImage(bitmap)
        }

        binding.btnDownload.setOnClickListener() {
            permissionNotification()

            Snackbar.make(requireView(), R.string.image_downloading, Snackbar.LENGTH_SHORT).show()

            CoroutineScope(Dispatchers.IO).launch {
                saveImage(Glide.with(requireContext())
                    .asBitmap()
                    .load("https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg") // sample image
                    .placeholder(android.R.drawable.progress_indeterminate_horizontal) // need placeholder to avoid issue like glide annotations
                    .error(android.R.drawable.stat_notify_error) // need error to avoid issue like glide annotations
                    .submit()
                    .get())
            }
        }

        binding.btnBack.setOnClickListener() {

            findNavController().popBackStack()

        }
    }


    private fun saveImage(image: Bitmap): String? {
        var savedImagePath: String? = null

        val dateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val formattedDateTime = dateTime.format(formatter)

        val imageFileName = "CreaVision_$formattedDateTime.jpg"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/CreaVision"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath)

            CoroutineScope(Dispatchers.Main).launch {
                Snackbar.make(requireView(), R.string.image_saved, Snackbar.LENGTH_SHORT).show()
            }
        } else {
            Snackbar.make(requireView(), R.string.image_could_not_be_downloaded, Snackbar.LENGTH_SHORT).show()

        }
        return savedImagePath
    }


    private fun galleryAddPic(imagePath: String?) {
        imagePath?.let { path ->
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val f = File(path)
            val contentUri: Uri = Uri.fromFile(f)
            mediaScanIntent.data = contentUri
            requireContext().sendBroadcast(mediaScanIntent)
        }
    }

    private fun resetUI() {
        binding.animationView.visibility = View.VISIBLE
        binding.imageView.setImageDrawable(null)
        binding.animationView.playAnimation()
        viewModel.reset()
    }
}