package com.example.testapp.domain.use_case.location

import com.example.testapp.data.repository.LocationResult
import com.example.testapp.domain.repository.LocationRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) {
    suspend operator fun invoke(): LocationResult {
        return locationRepository.getCurrentLocation()
    }
}