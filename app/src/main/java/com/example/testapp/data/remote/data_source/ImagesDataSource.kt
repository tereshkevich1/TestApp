package com.example.testapp.data.remote.data_source

import com.example.testapp.data.remote.dto.ImageDtoIn
import com.example.testapp.data.remote.dto.ImageDtoOut
import com.example.testapp.data.remote.util.NetworkResult

interface ImagesDataSource {
    suspend fun uploadImage(imageDtoIn: ImageDtoIn): NetworkResult<ImageDtoOut>
}