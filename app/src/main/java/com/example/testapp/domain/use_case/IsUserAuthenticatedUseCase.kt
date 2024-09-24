package com.example.testapp.domain.use_case

import com.example.testapp.data.common.utils.SharedPrefs
import javax.inject.Inject

class IsUserAuthenticatedUseCase @Inject constructor(private val sharedPrefs: SharedPrefs) {
    operator fun invoke(): Boolean = sharedPrefs.getToken().isNotEmpty()
}