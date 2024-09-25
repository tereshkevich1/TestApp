package com.example.testapp.data.remote.api

import com.example.testapp.data.remote.dto.image.ImageDtoIn
import com.example.testapp.data.remote.dto.image.ImageDtoOut
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ImagesApi {
    @POST("image")
    suspend fun uploadImage(@Body imageDtoIn: ImageDtoIn): Response<ImageDtoOut>
}