package com.example.testapp.domain.use_case

import com.example.testapp.data.remote.dto.SignUpResponseDto
import com.example.testapp.data.remote.dto.SignUserDtoIn
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase
) {
    suspend operator fun invoke(
        signUserDtoIn: SignUserDtoIn
    ): Flow<NetworkResult<SignUpResponseDto>> = flow {
        when (val result = repository.signIn(signUserDtoIn)) {
            is NetworkResult.Error -> {
                emit(NetworkResult.Error(result.code, result.message))
            }

            is NetworkResult.Exception -> {
                emit(NetworkResult.Exception(result.e))
            }

            is NetworkResult.Success -> {
                emit(NetworkResult.Success(result.data))
                saveAccessTokenUseCase(result.data.data.token)
            }
        }
    }.flowOn(Dispatchers.IO)
}