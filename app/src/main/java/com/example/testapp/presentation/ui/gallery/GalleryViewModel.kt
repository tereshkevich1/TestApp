package com.example.testapp.presentation.ui.gallery

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.domain.use_case.CreateImageFileUseCase
import com.example.testapp.domain.use_case.SaveImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val createImageFileUseCase: CreateImageFileUseCase,
    private val saveImageUseCase: SaveImageUseCase
) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text


    fun saveImage(bitmap: Bitmap?, mimeType: String, absolutePath: String?) {
        saveImageUseCase.invoke(bitmap, mimeType, absolutePath)
    }

    fun createNewImageFile(context: Context) = createImageFileUseCase.invoke(context)

}