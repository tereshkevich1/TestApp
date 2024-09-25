package com.example.testapp.data.remote.api

import com.example.testapp.data.remote.dto.auth.SignUpResponseDto
import com.example.testapp.data.remote.dto.auth.SignUserDtoIn
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("account/signup")
    suspend fun signUp(@Body signUserDtoIn: SignUserDtoIn): Response<SignUpResponseDto>

    @POST("account/signin")
    suspend fun signIn(@Body signUserDtoIn: SignUserDtoIn): Response<SignUpResponseDto>
}

