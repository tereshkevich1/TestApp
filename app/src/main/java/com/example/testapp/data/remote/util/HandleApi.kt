package com.example.testapp.data.remote.util

import com.example.testapp.data.remote.dto.common.SignUpErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any, E: Any> handleApi(
    execute: suspend () -> Response<WrappedResponse<T>>,
    errorClass: Class<E>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()

        if (response.isSuccessful && body != null) {
            body.data?.let { data ->
                NetworkResult.Success(data)
            } ?: run {
                NetworkResult.Error(code = response.code(), message = "Data is null")
            }
        } else {
            val errorBody = response.errorBody()?.string()
            var errorMessage: String? = null
            errorBody?.let {
                val gson = Gson()
                val errorResponse = gson.fromJson(it, errorClass)
                errorMessage = (errorResponse as? SignUpErrorResponse)?.error
            }
            NetworkResult.Error(code = response.code(), message = errorMessage)
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        NetworkResult.Exception(e)
    }
}

