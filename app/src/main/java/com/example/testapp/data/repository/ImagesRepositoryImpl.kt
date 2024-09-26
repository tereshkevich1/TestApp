package com.example.testapp.data.repository

import com.example.testapp.data.local.data_sorce.ImagesLocalDataSource
import com.example.testapp.data.local.entity.ImageEntity
import com.example.testapp.data.remote.data_source.image.ImagesRemoteDataSource
import com.example.testapp.data.remote.dto.image.ImageDtoIn
import com.example.testapp.data.remote.dto.image.ImageDtoOut
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesRemoteDataSource: ImagesRemoteDataSource,
    private val imagesLocalDataSource: ImagesLocalDataSource
) : ImagesRepository {

    override suspend fun uploadImage(
        imageDtoIn: ImageDtoIn,
        uri: String
    ): Pair<NetworkResult<ImageDtoOut>, Long> {
        val result = imagesRemoteDataSource.uploadImage(imageDtoIn)
        val newImageEntityId = when (result) {
            is NetworkResult.Success -> {
                saveLocalImage(
                    result.data.id.toString(), uri,
                    result.data.date,
                    result.data.lat,
                    result.data.lng
                )
            }

            else -> {
                saveLocalImage("", uri, imageDtoIn.date, imageDtoIn.lat, imageDtoIn.lng)
            }
        }
        return result to newImageEntityId
    }

    override suspend fun getPhotos(): List<ImageEntity> = imagesLocalDataSource.getPhotos()
    override suspend fun getImageById(id: Long): ImageEntity? =
        imagesLocalDataSource.getImageById(id)


    private suspend fun saveLocalImage(
        serverId: String,
        uri: String,
        date: Long,
        lat: Double,
        lng: Double
    ): Long {
        val localImage = ImageEntity(
            serverId = serverId,
            uri = uri,
            date = date,
            lat = lat,
            lng = lng
        )
        return imagesLocalDataSource.insertImage(localImage)
    }
}
