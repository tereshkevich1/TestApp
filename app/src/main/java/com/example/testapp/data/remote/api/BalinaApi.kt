package com.example.testapp.data.remote.api

import com.example.testapp.data.remote.dto.SignUpResponseDto
import com.example.testapp.data.remote.dto.SignUserDtoIn
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface BalinaApi {
    @POST("account/signup")
    suspend fun signUp(@Body signUserDtoIn: SignUserDtoIn): Response<SignUpResponseDto>

    @POST("account/signin")
    suspend fun signIn(@Query("login") login: String, @Query("password") password: String): Response<SignUpResponseDto>
}

