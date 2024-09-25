package com.example.testapp.presentation.ui.gallery

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.testapp.domain.use_case.image.BitmapToBase64UseCase
import com.example.testapp.domain.use_case.image.CreateFileUseCase
import com.example.testapp.domain.use_case.image.GetBitmapFromUriUseCase
import com.example.testapp.domain.use_case.image.SaveImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val createFileUseCase: CreateFileUseCase,
    private val getBitmapFromUriUseCase: GetBitmapFromUriUseCase,
    private val saveImageUseCase: SaveImageUseCase,
    private val bitmapToBase64UseCase: BitmapToBase64UseCase
) : ViewModel() {
    fun handleImageResult(imageBitmap: Bitmap?, onErrorCallback: () -> Unit) {
        if (imageBitmap == null) {
            onErrorCallback()
            return
        }
        try {
            val file = createFileUseCase.invoke()
            val base64: String =
                bitmapToBase64UseCase.invoke(imageBitmap, Bitmap.CompressFormat.JPEG)
            saveImageUseCase.invoke(imageBitmap, "image/jpeg", file.absolutePath)
        } catch (e: Exception) {
            onErrorCallback()
        }
    }
}