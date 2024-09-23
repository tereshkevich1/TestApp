package com.example.testapp.data.repository

import android.graphics.Bitmap
import com.example.testapp.domain.repository.ImageRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageRepositoryImpl : ImageRepository {
    override fun saveImage(bitmap: Bitmap?, mimeType: String, path: String, absolutePath: String?): Result<File> {
        return try {
            if (bitmap == null || absolutePath.isNullOrEmpty()) {
                return Result.failure(IOException("Bitmap or absolutePath is null"))
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
                        return Result.failure(IOException("Unsupported image format"))
                    }
                }
            }
            Result.success(file)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}