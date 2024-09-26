package com.example.testapp.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.remote.dto.auth.SignUserDtoIn
import com.example.testapp.data.remote.dto.auth.SignUserOutDto
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.domain.use_case.authentication.SignInUseCase
import com.example.testapp.domain.use_case.authentication.SignUpUseCase
import com.example.testapp.presentation.ui.util.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _signUp = MutableLiveData<ScreenUiState<SignUserOutDto>>()
    val signUp: LiveData<ScreenUiState<SignUserOutDto>> = _signUp

    private val _logIn = MutableLiveData<ScreenUiState<SignUserOutDto>>()
    val logIn: LiveData<ScreenUiState<SignUserOutDto>> = _logIn

    fun signIn(login: String, password: String) {
        viewModelScope.launch {
            signInUseCase.invoke(SignUserDtoIn(login, password)).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _logIn.value = ScreenUiState.Success(result.data)
                    }

                    is NetworkResult.Error -> {
                        _logIn.value = ScreenUiState.Error(result.message ?: "Unknown Error")
                    }

                    is NetworkResult.Exception -> {
                        _logIn.value =
                            ScreenUiState.Error(result.e.message ?: "Unknown Error")
                    }
                }
            }
        }
    }

    fun signUp(login: String, password: String) {
        viewModelScope.launch {
            signUpUseCase.invoke(SignUserDtoIn(login, password)).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _signUp.value = ScreenUiState.Success(result.data)
                    }

                    is NetworkResult.Error -> {
                        _signUp.value = ScreenUiState.Error(result.message ?: "Unknown Error")
                    }

                    is NetworkResult.Exception -> {
                        _signUp.value =
                            ScreenUiState.Error(result.e.message ?: "Unknown Error")
                    }
                }
            }
        }
    }
}