package com.example.testapp.data.local.data_sorce

import com.example.testapp.data.local.dao.ImagesDao
import com.example.testapp.data.local.entity.ImageEntity
import javax.inject.Inject

class ImagesLocalDataSourceImpl @Inject constructor(private val dao: ImagesDao) :
    ImagesLocalDataSource {
    override suspend fun getPhotos(): List<ImageEntity> {
        return dao.getPhotos()
    }

    override suspend fun insertImage(image: ImageEntity): Long {
        return dao.insertImage(image)
    }

    override suspend fun getImageById(id: Long): ImageEntity? {
        return dao.getImageById(id)
    }
}