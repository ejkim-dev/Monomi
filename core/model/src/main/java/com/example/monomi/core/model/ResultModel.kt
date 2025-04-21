package com.example.monomi.core.model

sealed class ResultModel<out T> {
    data class Success<out T>(val data: T) : ResultModel<T>()

    data class Error(
        val statusCode: Int,
        val errorType: String,
        val message: String?
    ) : ResultModel<Nothing>()

    data object Empty : ResultModel<Nothing>()
}