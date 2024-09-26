package com.example.testapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.data.local.entity.ImageEntity

@Dao
interface ImagesDao {
    @Query("SELECT * FROM photos")
    suspend fun getPhotos(): List<ImageEntity>

    @Insert
    suspend fun insertImage(image: ImageEntity): Long

    @Query("SELECT * FROM photos WHERE id = :id")
    suspend fun getImageById(id: Long): ImageEntity?
}