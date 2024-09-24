package com.example.testapp.domain.use_case

import com.example.testapp.data.common.utils.SharedPrefs
import javax.inject.Inject

class SaveAccessTokenUseCase @Inject constructor(private val sharedPrefs: SharedPrefs) {
    operator fun invoke(token: String) {
        sharedPrefs.saveToken(token)
    }
}