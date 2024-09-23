package com.example.testapp.presentation.ui.gallery

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.remote.dto.SignUserDtoIn
import com.example.testapp.data.remote.dto.SignUpResponseDto
import com.example.testapp.data.remote.util.NetworkResult
import com.example.testapp.domain.use_case.CreateImageFileUseCase
import com.example.testapp.domain.use_case.SaveImageUseCase
import com.example.testapp.domain.use_case.SignInUseCase
import com.example.testapp.domain.use_case.SignUpUseCase
import com.example.testapp.presentation.ui.util.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val createImageFileUseCase: CreateImageFileUseCase,
    private val saveImageUseCase: SaveImageUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text

    private val _logIn = MutableLiveData<ScreenUiState<SignUpResponseDto>>()
    val logIn: LiveData<ScreenUiState<SignUpResponseDto>> = _logIn


    fun saveImage(bitmap: Bitmap?, mimeType: String, absolutePath: String?) {
        saveImageUseCase.invoke(bitmap, mimeType, absolutePath)
    }

    fun createNewImageFile(context: Context) = createImageFileUseCase.invoke(context)

    fun signUp(login: String, password: String) {
        viewModelScope.launch {
            signInUseCase.invoke(SignUserDtoIn("striOIOGng","drghidrg")).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _logIn.value = ScreenUiState.Success(result.data)
                        Log.d("result", "${result.data}")
                    }

                    is NetworkResult.Error -> {
                        _logIn.value = ScreenUiState.Error(result.message ?: "Unknown Error")
                        Log.d("result", "${result.message}")
                    }

                    is NetworkResult.Exception -> {
                        _logIn.value =
                            ScreenUiState.Error(result.e.message ?: "Unknown Error")
                        Log.d("result", "${result.e.message}")
                    }
                }

            }
        }
    }

}