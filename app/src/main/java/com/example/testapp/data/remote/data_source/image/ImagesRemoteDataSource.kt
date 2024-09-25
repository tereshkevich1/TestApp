package com.example.testapp.data.remote.data_source.image

import com.example.testapp.data.remote.dto.image.ImageDtoIn
import com.example.testapp.data.remote.dto.image.ImageDtoOut
import com.example.testapp.data.remote.util.NetworkResult

interface ImagesRemoteDataSource {
    suspend fun uploadImage(imageDtoIn: ImageDtoIn): NetworkResult<ImageDtoOut>
}