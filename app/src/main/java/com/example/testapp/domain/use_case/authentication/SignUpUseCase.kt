package com.example.testapp.domain.use_case.authentication

import com.example.testapp.data.remote.dto.auth.SignUserDtoIn
import com.example.testapp.data.remote.dto.auth.SignUserOutDto
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase
) {
    suspend operator fun invoke(
        signUserDtoIn: SignUserDtoIn
    ): Flow<NetworkResult<SignUserOutDto>> = flow {
        when (val result = repository.signUp(signUserDtoIn)) {
            is NetworkResult.Error -> {
                emit(NetworkResult.Error(result.code, result.message))
            }

            is NetworkResult.Exception -> {
                emit(NetworkResult.Exception(result.e))
            }

            is NetworkResult.Success -> {
                emit(NetworkResult.Success(result.data))
                saveAccessTokenUseCase(result.data.token)
            }
        }
    }.flowOn(Dispatchers.IO)
}


