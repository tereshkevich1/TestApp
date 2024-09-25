package com.example.testapp.data.remote.data_source.auth

import com.example.testapp.data.remote.api.AuthApi
import com.example.testapp.data.remote.dto.common.SignUpErrorResponse
import com.example.testapp.data.remote.dto.auth.SignUpResponseDto
import com.example.testapp.data.remote.dto.auth.SignUserDtoIn
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.data.remote.util.handleApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(private val api: AuthApi) :
    AuthRemoteDataSource {
    override suspend fun signUp(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto> = withContext(Dispatchers.IO){
        handleApi (
         execute = { api.signUp(signUserDtoIn)},
            errorClass = SignUpErrorResponse::class.java
        )
    }

    override suspend fun signIn(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto> = withContext(Dispatchers.IO){
        handleApi (
            execute = { api.signIn(signUserDtoIn)},
            errorClass = SignUpErrorResponse::class.java
        )
    }
}