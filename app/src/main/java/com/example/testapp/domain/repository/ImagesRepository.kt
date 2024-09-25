package com.example.testapp.domain.repository

import com.example.testapp.data.remote.dto.ImageDtoIn
import com.example.testapp.data.remote.dto.ImageDtoOut
import com.example.testapp.data.remote.util.NetworkResult

interface ImagesRepository {
    suspend fun uploadImage(imageDtoIn: ImageDtoIn): NetworkResult<ImageDtoOut>
}