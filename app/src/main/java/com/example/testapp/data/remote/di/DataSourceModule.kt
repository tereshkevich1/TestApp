package com.example.testapp.data.remote.di

import com.example.testapp.data.remote.data_source.auth.AuthRemoteDataSource
import com.example.testapp.data.remote.data_source.auth.AuthRemoteDataSourceImpl
import com.example.testapp.data.remote.data_source.image.ImagesRemoteDataSource
import com.example.testapp.data.remote.data_source.image.ImagesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun provideAuthDataSource(impl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    fun provideImagesDataSource(impl: ImagesRemoteDataSourceImpl): ImagesRemoteDataSource
}