package com.example.testapp.domain.use_case.image.database_operations

import android.net.Uri
import com.example.testapp.domain.model.ImageWithBitmap
import com.example.testapp.domain.repository.ImagesRepository
import com.example.testapp.domain.use_case.date.DateToStringUseCase
import com.example.testapp.domain.use_case.image.image_decoding.GetBitmapFromUriUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetImageByIdUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository,
    private val dateToStringUseCase: DateToStringUseCase,
    private val getBitmapFromUriUseCase: GetBitmapFromUriUseCase
) {
    suspend operator fun invoke(id: Long): ImageWithBitmap? = withContext(Dispatchers.IO) {
        val imageEntity = imagesRepository.getImageById(id) ?: return@withContext null

        val uri = Uri.parse(imageEntity.uri)
        val bitmap = getBitmapFromUriUseCase.invoke(uri)

        val formattedDate = dateToStringUseCase.invoke(
            milliSeconds = imageEntity.date,
            format = "yyyy-MM-dd"
        )

        ImageWithBitmap(
            id = imageEntity.id,
            serverId = imageEntity.serverId,
            bitmap = bitmap,
            date = formattedDate,
            lat = imageEntity.lat,
            lng = imageEntity.lng
        )
    }
}
