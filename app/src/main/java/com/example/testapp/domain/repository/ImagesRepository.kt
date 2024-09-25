package com.example.testapp.domain.repository

import com.example.testapp.data.local.entity.ImageEntity
import com.example.testapp.data.remote.dto.image.ImageDtoIn
import com.example.testapp.data.remote.dto.image.ImageDtoOut
import com.example.testapp.data.remote.util.NetworkResult

interface ImagesRepository {
    suspend fun uploadImage(imageDtoIn: ImageDtoIn, uri: String): NetworkResult<ImageDtoOut>
    suspend fun getPhotos(): List<ImageEntity>
}