package com.twitter.simulation.android.data.response


sealed class Response<out T : Any> {
    data class Success<out T : Any>(val data: T) : Response<T>()
    data class Error(
        val exception: Exception,
        val errorMessage: String?,
    ) : Response<Nothing>()

    data class Loading(val loading: Boolean) : Response<Nothing>()
}