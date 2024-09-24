package com.example.testapp.data.remote.dto

data class ImageDtoIn(
    val base64Image: String,
    val date: Long,
    val lat: Double,
    val lng: Double
)