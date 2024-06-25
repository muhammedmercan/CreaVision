package com.ai.creavision.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.ai.creavision.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


open class BaseFragment : Fragment() {

    var allPermissionsGranted = false

    private val appPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if (result.all { it.value }) {

                allPermissionsGranted = true
            } else {
                allPermissionsGranted = false
                Toast.makeText(requireContext(), "Permisson needed!", Toast.LENGTH_LONG)
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    fun permissionNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                ) {
                    Snackbar.make(
                        requireView(),
                        "Permission needed for gallery",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Give Permission",
                        View.OnClickListener {
                            appPermissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))

                        }).show()
                } else {//Eğer daha önce sorulmamışsa yapılacak işlemler
                    appPermissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))
                }
            } else {
                allPermissionsGranted = true
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    Snackbar.make(
                        requireView(),
                        "Permission needed for gallery",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Give Permission",
                        View.OnClickListener {
                            appPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                )
                            )
                        }).show()
                } else {//Eğer daha önce sorulmamışsa yapılacak işlemler
                    appPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    )
                }
            }
            else {
                allPermissionsGranted = true
            }
        }
    }

    fun shareImage(bitmap: Bitmap) {
        val imageUri = saveBitmapToCache(bitmap)
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/png"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Paylaş"))
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

    fun saveImage(image: Bitmap): String? {

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

    fun saveImageToInternalStorage(image: Bitmap): String? {

        var savedImagePath: String? = null

        val dateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val formattedDateTime = dateTime.format(formatter)

        val imageFileName = "CreaVision_$formattedDateTime.jpg"
        val storageDir = File(context?.filesDir,"favorites")

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
            //galleryAddPic(savedImagePath)

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
}