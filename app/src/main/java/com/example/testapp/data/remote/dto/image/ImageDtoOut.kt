package com.example.testapp.data.remote.dto.image

data class ImageDtoOut(
    val id: Int,
    val url: String,
    val date: Long,
    val lat: Double,
    val lng: Double
)