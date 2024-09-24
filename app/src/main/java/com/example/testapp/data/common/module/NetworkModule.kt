package com.example.testapp.data.common.module

import com.example.testapp.data.common.utils.RequestInterceptor
import com.example.testapp.data.common.utils.SharedPrefs
import com.example.testapp.data.remote.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(requestInterceptor: RequestInterceptor) =
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            addInterceptor(requestInterceptor)
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl("https://junior.balinasoft.com/api/")
        }.build()


    @Provides
    @Singleton
    fun provideCurrencyExchangeApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideRequestInterceptor(prefs: SharedPrefs): RequestInterceptor {
        return RequestInterceptor(prefs)
    }
}