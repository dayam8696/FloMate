package com.example.flomate.controller


enum class ApiStatus {
    SUCCESS,
    ERROR,
    LOADING
}

sealed class ApiResult<out T>(val status: ApiStatus, val data: T?, val message: String?) {

    data class Success<out T>(private val _data: T?) : ApiResult<T>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null
    )

    data class Error(private val exception: String) : ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        message = exception
    )

    object Loading : ApiResult<Nothing>(
        status = ApiStatus.LOADING,
        null,
        null
    )
}
