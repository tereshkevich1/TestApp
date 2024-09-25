package com.example.testapp.data.repository

import com.example.testapp.data.remote.dto.auth.SignUserDtoIn
import com.example.testapp.data.remote.data_source.auth.AuthRemoteDataSource
import com.example.testapp.data.remote.dto.auth.SignUpResponseDto
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authRemoteDataSource: AuthRemoteDataSource): AuthRepository {
    override suspend fun signUp(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto> {
        return authRemoteDataSource.signUp(signUserDtoIn)
    }

    override suspend fun signIn(signUserDtoIn: SignUserDtoIn): NetworkResult<SignUpResponseDto> {
        return authRemoteDataSource.signIn(signUserDtoIn)
    }
}