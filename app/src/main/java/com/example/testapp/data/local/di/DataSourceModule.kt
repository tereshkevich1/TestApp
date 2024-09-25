package com.example.testapp.data.local.di

import com.example.testapp.data.local.data_sorce.ImagesLocalDataSource
import com.example.testapp.data.local.data_sorce.ImagesLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun provideImagesLocalDataSource(impl: ImagesLocalDataSourceImpl): ImagesLocalDataSource
}