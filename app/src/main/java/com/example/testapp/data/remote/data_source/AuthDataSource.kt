package com.example.testapp.data.remote.data_source

import com.example.testapp.data.remote.dto.SignUserDtoIn
import com.example.testapp.data.remote.dto.SignUpResponseDto
import com.example.testapp.data.remote.util.NetworkResult

interface AuthDataSource {
    suspend fun signUp(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto>
    suspend fun signIn(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto>
}