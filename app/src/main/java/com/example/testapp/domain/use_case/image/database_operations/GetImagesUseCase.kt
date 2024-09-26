package com.example.testapp.domain.use_case.image.database_operations

import android.net.Uri
import com.example.testapp.domain.model.ImageWithBitmap
import com.example.testapp.domain.repository.ImagesRepository
import com.example.testapp.domain.use_case.date.DateToStringUseCase
import com.example.testapp.domain.use_case.image.image_decoding.GetBitmapFromUriUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository,
    private val dateToStringUseCase: DateToStringUseCase,
    private val getBitmapFromUriUseCase: GetBitmapFromUriUseCase
) {
    suspend operator fun invoke(): MutableList<ImageWithBitmap> = withContext(Dispatchers.IO) {
        val imageEntities = imagesRepository.getPhotos()

        imageEntities.map { entity ->
            val uri = Uri.parse(entity.uri)
            val bitmap = getBitmapFromUriUseCase.invoke(uri)

            val formattedDate = dateToStringUseCase.invoke(
                milliSeconds = entity.date,
                format = "yyyy-MM-dd"
            )

            ImageWithBitmap(
                id = entity.id,
                serverId = entity.serverId,
                bitmap = bitmap,
                date = formattedDate,
                lat = entity.lat,
                lng = entity.lng
            )
        }
    }.toMutableList()
}