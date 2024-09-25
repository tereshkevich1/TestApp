package com.example.testapp.data.repository

import android.util.Log
import com.example.testapp.data.local.data_sorce.ImagesLocalDataSource
import com.example.testapp.data.local.entity.ImageEntity
import com.example.testapp.data.remote.data_source.image.ImagesRemoteDataSource
import com.example.testapp.data.remote.dto.image.ImageDtoIn
import com.example.testapp.data.remote.dto.image.ImageDtoOut
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.domain.repository.ImagesRepository
import com.example.testapp.domain.use_case.date.GetCurrentTimestampUseCase
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesRemoteDataSource: ImagesRemoteDataSource,
    private val imagesLocalDataSource: ImagesLocalDataSource,
    private val getCurrentTimestampUseCase: GetCurrentTimestampUseCase
) : ImagesRepository {

    override suspend fun uploadImage(
        imageDtoIn: ImageDtoIn,
        uri: String
    ): NetworkResult<ImageDtoOut> {
        val result = imagesRemoteDataSource.uploadImage(imageDtoIn)
        when (result) {
            is NetworkResult.Success -> {
                saveLocalImage(
                    result.data.id.toString(), uri,
                    getCurrentTimestampUseCase.invoke(),
                    result.data.lat,
                    result.data.lng
                )
            }

            else -> {
                saveLocalImage("", uri, imageDtoIn.date, imageDtoIn.lat, imageDtoIn.lng)
            }
        }
        return result
    }

    override suspend fun getPhotos(): List<ImageEntity> = imagesLocalDataSource.getPhotos()

    private suspend fun saveLocalImage(
        serverId: String,
        uri: String,
        date: Long,
        lat: Double,
        lng: Double
    ) {
        Log.d("ImagesRepository", "Saving Local Image with date: $date")
        val localImage = ImageEntity(
            serverId = serverId,
            uri = uri,
            date = date,
            lat = lat,
            lng = lng
        )
        imagesLocalDataSource.insertImage(localImage)
    }
}
