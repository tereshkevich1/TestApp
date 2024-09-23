package com.example.testapp.data.remote.data_source

import com.example.testapp.data.remote.api.BalinaApi
import com.example.testapp.data.remote.dto.SignUserDtoIn
import com.example.testapp.data.remote.dto.SignUpResponseDto
import com.example.testapp.data.remote.dto.SignUserOutDto
import com.example.testapp.data.remote.dto.SignUpErrorResponse
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.data.remote.util.handleApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(private val api: BalinaApi) : AuthDataSource {
    override suspend fun signUp(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto> = withContext(Dispatchers.IO){
        handleApi (
         execute = { api.signUp(signUserDtoIn)},
            errorClass = SignUpErrorResponse::class.java
        )
    }

    override suspend fun signIn(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto> = withContext(Dispatchers.IO){
        handleApi (
            execute = { api.signUp(signUserDtoIn)},
            errorClass = SignUpErrorResponse::class.java
        )
    }
}