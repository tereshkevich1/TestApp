package com.example.testapp.domain.use_case.image

import android.graphics.Bitmap
import com.example.testapp.data.remote.dto.image.ImageDtoIn
import com.example.testapp.data.remote.dto.image.ImageDtoOut
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.domain.repository.ImagesRepository
import com.example.testapp.domain.use_case.date.GetCurrentTimestampUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: ImagesRepository,
    private val bitmapToBase64UseCase: BitmapToBase64UseCase,
    private val getCurrentTimestampUseCase: GetCurrentTimestampUseCase
) {
    suspend operator fun invoke(
        bitmap: Bitmap,
        uri: String,
        lat: Double,
        lng: Double
    ): Flow<Pair<NetworkResult<ImageDtoOut>, Long>> {
        val base64 = bitmapToBase64UseCase.invoke(bitmap, Bitmap.CompressFormat.JPEG)
        val timestamp = getCurrentTimestampUseCase.invoke()
        val imageDtoIn = ImageDtoIn(base64, timestamp, lat, lng)
        return flow {
            val result = repository.uploadImage(imageDtoIn, uri)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}