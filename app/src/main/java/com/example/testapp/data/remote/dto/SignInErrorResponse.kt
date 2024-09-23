package com.example.testapp.data.remote.dto

data class SignInErrorResponse(
    val status: Int,
    val error: String,
    val valid: List<ValidationError>
)

data class ValidationError(
    val field: String,
    val message: String
)