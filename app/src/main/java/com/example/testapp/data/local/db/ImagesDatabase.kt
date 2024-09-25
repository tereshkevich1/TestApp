package com.example.testapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.data.local.dao.ImagesDao
import com.example.testapp.data.local.entity.ImageEntity

@Database(entities = [ImageEntity::class], version = 1, exportSchema = false)
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun imageDao(): ImagesDao

    companion object {
        const val DATABASE_NAME = "photo_db"
    }
}