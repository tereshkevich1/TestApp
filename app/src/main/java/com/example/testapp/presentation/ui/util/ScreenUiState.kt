package com.example.testapp.presentation.ui.util

sealed class ScreenUiState<out T> {
    data object Empty : ScreenUiState<Nothing>()
    data object Loading : ScreenUiState<Nothing>()
    data class Success<out T>(val data: T) : ScreenUiState<T>()
    data class Error(val message: String) : ScreenUiState<Nothing>()
}