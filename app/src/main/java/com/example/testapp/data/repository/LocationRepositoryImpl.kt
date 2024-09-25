package com.example.testapp.data.repository

import android.annotation.SuppressLint
import com.example.testapp.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LocationRepositoryImpl @Inject constructor(private val fusedLocationProviderClient: FusedLocationProviderClient) :
    LocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): LocationResult {
        return try {
            val location = withContext(Dispatchers.IO) {
                suspendCoroutine { continuation ->
                    fusedLocationProviderClient.lastLocation
                        .addOnSuccessListener { location ->
                            if (location != null) {
                                continuation.resume(location)
                            } else {
                                continuation.resumeWithException(Exception("Location is null"))
                            }
                        }
                        .addOnFailureListener {
                            continuation.resumeWithException(it)
                        }
                }
            }
            LocationResult.Success(location.latitude, location.longitude)
        } catch (e: Exception) {
            LocationResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}

