package com.example.testapp.domain.use_case.date

import javax.inject.Inject

class GetCurrentTimestampUseCase @Inject constructor() {
    operator fun invoke(): Long {
        val currentTime = System.currentTimeMillis() / 1000
        return currentTime
    }
}