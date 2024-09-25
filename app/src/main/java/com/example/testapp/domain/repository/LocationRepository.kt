package com.example.testapp.domain.repository

import com.example.testapp.data.repository.LocationResult


interface LocationRepository {
    suspend fun getCurrentLocation(): LocationResult
}