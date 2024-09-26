package com.example.testapp.data.remote.util

data class WrappedResponse<T>(
    val status: Int,
    val data: T?
)