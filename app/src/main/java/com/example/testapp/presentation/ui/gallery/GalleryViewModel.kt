package com.example.testapp.presentation.ui.gallery

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.remote.dto.image.ImageDtoOut
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.domain.model.ImageWithBitmap
import com.example.testapp.domain.use_case.image.UploadImageUseCase
import com.example.testapp.domain.use_case.image.database_operations.GetImageByIdUseCase
import com.example.testapp.domain.use_case.image.database_operations.GetImagesUseCase
import com.example.testapp.domain.use_case.image.file_operations.CreateFileUseCase
import com.example.testapp.domain.use_case.image.file_operations.GetUriForFileUseCase
import com.example.testapp.domain.use_case.image.file_operations.SaveImageUseCase
import com.example.testapp.presentation.ui.util.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val createFileUseCase: CreateFileUseCase,
    private val saveImageUseCase: SaveImageUseCase,
    private val getUriForFileUseCase: GetUriForFileUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val getImagesUseCase: GetImagesUseCase,
    private val getImageByIdUseCase: GetImageByIdUseCase
) : ViewModel() {

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private val _images = MutableLiveData<MutableList<ImageWithBitmap>>()
    val images: LiveData<MutableList<ImageWithBitmap>> = _images

    private val _uploadImage = MutableLiveData<ScreenUiState<ImageDtoOut>>()
    val uploadImage: LiveData<ScreenUiState<ImageDtoOut>> = _uploadImage

    init {
        viewModelScope.launch {
            getImages()
        }
    }

    private fun uploadImage(
        imageBitmap: Bitmap,
        uri: String,
    ) {
        viewModelScope.launch {
            uploadImageUseCase.invoke(imageBitmap, uri, latitude, longitude).collect { result ->
                val networkResult = result.first
                val image = getImageByIdUseCase.invoke(result.second)
                image?.let { addImageToList(it) }

                when (networkResult) {
                    is NetworkResult.Success -> {
                        _uploadImage.value = ScreenUiState.Success(networkResult.data)
                    }

                    is NetworkResult.Error -> {
                        _uploadImage.value =
                            ScreenUiState.Error(networkResult.message ?: "Unknown Error")
                    }

                    is NetworkResult.Exception -> {
                        _uploadImage.value =
                            ScreenUiState.Error(networkResult.e.message ?: "Unknown Error")
                    }
                }
            }
        }
    }


    fun handleImageResult(
        imageBitmap: Bitmap?,
        onErrorCallback: () -> Unit
    ) {
        if (imageBitmap == null) {
            onErrorCallback()
            return
        }
        try {
            val file = createFileUseCase.invoke()
            val uri = getUriForFileUseCase.invoke(file).toString()

            saveImageUseCase.invoke(imageBitmap, "image/jpeg", file.absolutePath)
            uploadImage(imageBitmap, uri)
        } catch (e: Exception) {
            onErrorCallback()
        }
    }

    private fun getImages() {
        viewModelScope.launch {
            _images.value = getImagesUseCase.invoke()
        }
    }

    private fun addImageToList(image: ImageWithBitmap) {
        val updatedList = _images.value?.toMutableList() ?: mutableListOf()
        updatedList.add(image)
        _images.value = updatedList
    }

    fun setCoordinates(lat: Double, lng: Double) {
        Log.d("GalleryViewModel", "setCoordinates: $lat, $lng")
        latitude = lat
        longitude = lng
    }
}