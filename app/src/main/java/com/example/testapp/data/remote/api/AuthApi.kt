package com.example.testapp.data.remote.api

import com.example.testapp.data.remote.dto.auth.SignUserDtoIn
import com.example.testapp.data.remote.dto.auth.SignUserOutDto
import com.example.testapp.data.remote.util.WrappedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("account/signup")
    suspend fun signUp(@Body signUserDtoIn: SignUserDtoIn): Response<WrappedResponse<SignUserOutDto>>

    @POST("account/signin")
    suspend fun signIn(@Body signUserDtoIn: SignUserDtoIn): Response<WrappedResponse<SignUserOutDto>>
}

