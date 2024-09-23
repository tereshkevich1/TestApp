package com.example.testapp.presentation.ui.gallery

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testapp.databinding.FragmentGalleryBinding
import com.example.testapp.presentation.ui.util.Permission
import com.example.testapp.presentation.ui.util.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val galleryViewModel: GalleryViewModel by viewModels()

    private val binding get() = _binding!!

    private val permissionManager = PermissionManager.from(this)


    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val uri: Uri? = data?.data

                if (uri == null) {
                    showError()
                    return@registerForActivityResult
                }

                val bitmap: Bitmap = if (Build.VERSION.SDK_INT < 29) {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }

                galleryViewModel.saveImage(bitmap, "image/jpeg", uri.path)
            }
        }

    private fun showError() {
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.openCameraButton.setOnClickListener {
            handlePermissions()
        }

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handlePermissions() {
        permissionManager
            .request(Permission.Camera, Permission.Location)
            .checkPermission { grantResults ->
                val cameraGranted = grantResults[Permission.Camera.permissions[0]] ?: false
                val locationGranted = grantResults[Permission.Location.permissions[0]] ?: false

                if (cameraGranted) {
                    if (locationGranted) {
                        openCamera()
                    } else {
                        openCamera()
                    }
                } else {
                    Log.d("permissions", "camera no")
                }
            }
    }


    private fun openCamera() {
        try {
            val imageFile = galleryViewModel.createNewImageFile(requireContext())
            val imageUri = FileProvider.getUriForFile(
                requireActivity(),
                "com.example.testapp.provider",
                imageFile
            )

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            }

            activityResultLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }


    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            100 -> {
                val resultBitmap: Bitmap =
                    BitmapFactory.decodeFile(mediaPath.absolutePath)//Save file to upload on server
                binding.imageView2.setImageBitmap(resultBitmap)
                val file = saveBitmapToFile(resultBitmap, "image/jpg", mediaPath.absolutePath)
            }
        }
    }*/

    /*
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            mediaPath = createNewImageFile(requireActivity())
            val uri = FileProvider.getUriForFile(
                requireActivity(),
                "com.example.testapp.provider",
                mediaPath
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(takePictureIntent, 100)


        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }*/

    /*
    @Throws(IOException::class)
    fun createNewImageFile(context: Context): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            absolutePath
        }
    }*/

    /*
    private fun saveBitmapToFile(bitmap: Bitmap?, mimeType: String, absolutePath: String?): File? {
        if (bitmap == null || absolutePath.isNullOrEmpty()) {
            return null
        }

        val file = File(absolutePath)

        FileOutputStream(file).use { stream ->
            when {
                mimeType.contains("jpg", ignoreCase = true) || mimeType.contains(
                    "jpeg",
                    ignoreCase = true
                ) -> {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
                }

                mimeType.contains("png", ignoreCase = true) -> {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                }

                else -> {
                    return null
                }
            }
        }
        return file
    }*/

}