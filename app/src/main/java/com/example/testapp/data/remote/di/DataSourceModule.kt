package com.example.testapp.data.remote.di

import com.example.testapp.data.remote.data_source.AuthDataSource
import com.example.testapp.data.remote.data_source.AuthDataSourceImpl
import com.example.testapp.data.remote.data_source.ImagesDataSource
import com.example.testapp.data.remote.data_source.ImagesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun provideAuthDataSource(impl: AuthDataSourceImpl): AuthDataSource

    @Binds
    fun provideImagesDataSource(impl: ImagesDataSourceImpl): ImagesDataSource
}