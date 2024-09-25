package com.example.testapp.domain.use_case.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetBitmapFromUriUseCase @Inject constructor(@ApplicationContext private val context: Context) {
    operator fun invoke(uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT < 29) {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    }
}