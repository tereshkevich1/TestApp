package com.example.testapp.domain.repository

import com.example.testapp.data.remote.dto.auth.SignUserDtoIn
import com.example.testapp.data.remote.dto.auth.SignUserOutDto
import com.example.testapp.data.remote.util.NetworkResult

interface AuthRepository {
    suspend fun signUp(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUserOutDto>
    suspend fun signIn(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUserOutDto>
}