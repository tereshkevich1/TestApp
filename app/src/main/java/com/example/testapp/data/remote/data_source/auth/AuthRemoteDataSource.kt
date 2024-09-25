package com.example.testapp.data.remote.data_source.auth

import com.example.testapp.data.remote.dto.auth.SignUserDtoIn
import com.example.testapp.data.remote.dto.auth.SignUpResponseDto
import com.example.testapp.data.remote.util.NetworkResult

interface AuthRemoteDataSource {
    suspend fun signUp(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto>
    suspend fun signIn(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto>
}