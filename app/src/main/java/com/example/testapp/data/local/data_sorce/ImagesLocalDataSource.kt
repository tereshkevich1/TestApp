package com.example.testapp.data.local.data_sorce

import com.example.testapp.data.local.entity.ImageEntity

interface ImagesLocalDataSource {
    suspend fun getPhotos(): List<ImageEntity>
    suspend fun insertImage(image: ImageEntity): Long
    suspend fun getImageById(id: Long): ImageEntity?
}