package com.example.testapp.domain.model

import android.graphics.Bitmap

data class ImageWithBitmap(
    val id: Long,
    val serverId: String,
    val bitmap: Bitmap,
    val date: String,
    val lat: Double?,
    val lng: Double?
)