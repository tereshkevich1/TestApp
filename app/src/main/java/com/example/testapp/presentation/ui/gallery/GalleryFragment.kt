package com.example.testapp.presentation.ui.gallery

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.data.repository.LocationResult
import com.example.testapp.databinding.FragmentGalleryBinding
import com.example.testapp.presentation.ui.gallery.adapter.ImageRecyclerViewAdapter
import com.example.testapp.presentation.ui.gallery.adapter.OnImageClickListener
import com.example.testapp.presentation.ui.util.Permission
import com.example.testapp.presentation.ui.util.PermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private val galleryViewModel: GalleryViewModel by viewModels()
    private val authCheckViewModel: AuthCheckViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()
    private val permissionManager = PermissionManager.from(this)
    private lateinit var adapter: ImageRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                galleryViewModel.handleImageResult(imageBitmap) {
                    showError()
                }
            } else {
                showError()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authCheckViewModel.isAuthenticated.observe(this) {
            if (!it) findNavController().navigate(R.id.action_galleryFragment_to_auth_navigation)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
        setupRecyclerView()

        observeLocation()

        binding.openCameraButton.setOnClickListener {
            handlePermissions()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        val onImageClickListener = object : OnImageClickListener {}
        recyclerView = binding.cartRC

        adapter = ImageRecyclerViewAdapter(
            onImageClickListener
        )
        recyclerView.adapter = adapter

        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = adapter
        }

        galleryViewModel.images.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun handlePermissions() {
        permissionManager
            .request(Permission.Camera, Permission.Location)
            .checkPermission { grantResults ->
                val cameraGranted = grantResults[Permission.Camera.permissions[0]] ?: false
                val locationGranted = grantResults[Permission.Location.permissions[0]] ?: false

                if (cameraGranted) {
                    if (locationGranted) {
                        locationViewModel.fetchCurrentLocation()
                        openCamera()
                    } else {
                        openCamera() //save image without location
                    }
                } else {
                    // Handle permission denied
                }
            }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activityResultLauncher.launch(intent)
    }

    private fun observeLocation() {
        locationViewModel.location.observe(viewLifecycleOwner) { locationResult ->
            // Assuming locationResult contains latitude and longitude
            Log.d("GalleryViewModel", locationResult.toString())
            when (locationResult) {
                is LocationResult.Success -> {
                    val latitude = locationResult.latitude
                    val longitude = locationResult.longitude
                    galleryViewModel.setCoordinates(latitude, longitude)
                }

                is LocationResult.Error -> {
                    // Handle error
                }
            }
        }
    }

    private fun showError() {
        // Handle error
    }
}