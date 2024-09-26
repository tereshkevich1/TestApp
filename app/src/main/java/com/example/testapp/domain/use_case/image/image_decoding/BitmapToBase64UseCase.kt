package com.example.testapp.domain.use_case.image.image_decoding

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class BitmapToBase64UseCase @Inject constructor() {
    operator fun invoke(bitmap: Bitmap, format: Bitmap.CompressFormat): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(format, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }
}