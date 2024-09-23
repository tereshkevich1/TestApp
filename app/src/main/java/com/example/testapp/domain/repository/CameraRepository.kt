package com.example.testapp.domain.repository

import android.graphics.Bitmap
import java.io.File

interface ImageRepository {
    fun saveImage(bitmap: Bitmap?, mimeType: String, path: String, absolutePath: String?): Result<File>
}