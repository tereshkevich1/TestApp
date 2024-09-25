package com.example.testapp.data.repository

import com.example.testapp.data.remote.data_source.ImagesDataSource
import com.example.testapp.data.remote.dto.ImageDtoIn
import com.example.testapp.data.remote.dto.ImageDtoOut
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(private val imagesDataSource: ImagesDataSource) :
    ImagesRepository {
    override suspend fun uploadImage(imageDtoIn: ImageDtoIn): NetworkResult<ImageDtoOut> {
        return imagesDataSource.uploadImage(imageDtoIn)
    }
}