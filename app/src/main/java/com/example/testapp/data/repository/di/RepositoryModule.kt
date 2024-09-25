package com.example.testapp.data.repository.di

import com.example.testapp.data.repository.AuthRepositoryImpl
import com.example.testapp.data.repository.ImagesRepositoryImpl
import com.example.testapp.data.repository.LocationRepositoryImpl
import com.example.testapp.domain.repository.AuthRepository
import com.example.testapp.domain.repository.ImagesRepository
import com.example.testapp.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideImagesRepository(impl: ImagesRepositoryImpl): ImagesRepository

    @Binds
    fun provideLocationRepository(impl: LocationRepositoryImpl): LocationRepository
}