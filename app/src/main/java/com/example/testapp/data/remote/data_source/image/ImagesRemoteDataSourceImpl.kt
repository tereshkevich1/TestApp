package com.example.testapp.data.remote.data_source.image

import com.example.testapp.data.remote.api.ImagesApi
import com.example.testapp.data.remote.dto.image.ImageDtoIn
import com.example.testapp.data.remote.dto.image.ImageDtoOut
import com.example.testapp.data.remote.dto.common.SignUpErrorResponse
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.data.remote.util.handleApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImagesRemoteDataSourceImpl @Inject constructor(private val api: ImagesApi) :
    ImagesRemoteDataSource {
    override suspend fun uploadImage(imageDtoIn: ImageDtoIn): NetworkResult<ImageDtoOut> =
        withContext(Dispatchers.IO) {
            handleApi(
                execute = { api.uploadImage(imageDtoIn) },
                errorClass = SignUpErrorResponse::class.java
            )
        }
}