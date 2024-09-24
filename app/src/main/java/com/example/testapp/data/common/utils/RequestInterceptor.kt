package com.example.testapp.data.common.utils

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(private val pref: SharedPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = pref.getToken()
        val request = chain.request().newBuilder()
        if (token.isNotEmpty()) {
            request.addHeader("Access-Token", token)
        }
        return chain.proceed(request.build())
    }
}