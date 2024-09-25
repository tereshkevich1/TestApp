package com.example.testapp.data.remote.data_source

import com.example.testapp.data.remote.api.ImagesApi
import com.example.testapp.data.remote.dto.ImageDtoIn
import com.example.testapp.data.remote.dto.ImageDtoOut
import com.example.testapp.data.remote.dto.SignUpErrorResponse
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.data.remote.util.handleApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImagesDataSourceImpl @Inject constructor(private val api: ImagesApi) : ImagesDataSource {
    override suspend fun uploadImage(imageDtoIn: ImageDtoIn): NetworkResult<ImageDtoOut> =
        withContext(Dispatchers.IO) {
            handleApi(
                execute = { api.uploadImage(imageDtoIn) },
                errorClass = SignUpErrorResponse::class.java
            )
        }
}