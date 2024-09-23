package com.example.testapp.data.repository

import com.example.testapp.data.remote.dto.SignUserDtoIn
import com.example.testapp.data.remote.data_source.AuthDataSource
import com.example.testapp.data.remote.dto.SignUpResponseDto
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource): AuthRepository {
    override suspend fun signUp(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto> {
        return authDataSource.signUp(signUserDtoIn)
    }

    override suspend fun signIn(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto> {
        return authDataSource.signIn(signUserDtoIn)
    }
}