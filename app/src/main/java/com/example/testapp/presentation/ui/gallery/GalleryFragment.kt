package com.example.testapp.presentation.ui.gallery

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentGalleryBinding
import com.example.testapp.presentation.ui.util.Permission
import com.example.testapp.presentation.ui.util.PermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private val galleryViewModel: GalleryViewModel by viewModels()
    private val authCheckViewModel: AuthCheckViewModel by viewModels()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authCheckViewModel.isAuthenticated.observe(this) {
            if (!it) {
                findNavController().navigate(R.id.action_galleryFragment_to_auth_navigation)
            }
        }
        //findNavController().navigate(R.id.action_galleryFragment_to_auth_navigation)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)


        binding.openCameraButton.setOnClickListener {
            //handlePermissions()
            galleryViewModel.signUp("soegisrg", "seigjosi")
        }

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return binding.root
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
}