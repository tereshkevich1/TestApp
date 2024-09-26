package com.example.testapp.data.remote.dto.auth

data class SignUserOutDto(
    val userId: Int,
    val login: String,
    val token: String
)