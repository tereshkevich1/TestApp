package com.example.testapp.domain.use_case.image.file_operations

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class GetUriForFileUseCase @Inject constructor(@ApplicationContext private val context: Context) {
    operator fun invoke(imageFile: File): Uri = FileProvider.getUriForFile(
        context,
        "com.example.testapp.provider",
        imageFile
    )
}