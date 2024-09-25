package com.example.testapp.domain.use_case.date

import javax.inject.Inject

class GetCurrentTimestampUseCase @Inject constructor() {
    operator fun invoke(): Long {
        return System.currentTimeMillis()
    }
}