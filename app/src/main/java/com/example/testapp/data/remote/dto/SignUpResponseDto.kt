package com.example.testapp.data.remote.dto

data class SignUpResponseDto (
    val status: Int,
    val data: SignUserOutDto
)

data class SignUserOutDto(
    val userId: Int,
    val login: String,
    val token: String
)