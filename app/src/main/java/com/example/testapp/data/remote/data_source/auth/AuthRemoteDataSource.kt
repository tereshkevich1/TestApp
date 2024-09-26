package com.example.testapp.data.remote.data_source.auth

import com.example.testapp.data.remote.dto.auth.SignUserDtoIn
import com.example.testapp.data.remote.dto.auth.SignUserOutDto
import com.example.testapp.data.remote.util.NetworkResult

interface AuthRemoteDataSource {
    suspend fun signUp(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUserOutDto>
    suspend fun signIn(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUserOutDto>
}