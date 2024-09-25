package com.example.testapp.data.common.module

import android.app.Application
import androidx.room.Room
import com.example.testapp.data.local.dao.ImagesDao
import com.example.testapp.data.local.db.ImagesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideDataBase(app: Application): ImagesDatabase {
        return Room.databaseBuilder(
            app,
            ImagesDatabase::class.java,
            ImagesDatabase.DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideImagesDao(database: ImagesDatabase): ImagesDao {
        return database.imageDao()
    }
}