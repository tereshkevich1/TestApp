package com.example.testapp.domain.use_case.image.file_operations

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class SaveImageUseCase @Inject constructor() {
    operator fun invoke(
        bitmap: Bitmap?,
        mimeType: String,
        absolutePath: String?
    ): Result<File> {
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
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
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