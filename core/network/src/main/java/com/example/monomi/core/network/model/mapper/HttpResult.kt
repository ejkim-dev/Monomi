package com.example.monomi.core.network.model.mapper

import com.example.monomi.core.network.model.MonomiErrorResponse
import com.example.monomi.core.model.ResultModel
import kotlinx.serialization.json.Json
import retrofit2.Response

inline fun <reified T> Response<T>.mapToResultModel(
    json: Json = Json { ignoreUnknownKeys = true }
): ResultModel<T> {
    return if (isSuccessful) {
        val body = body() ?: return ResultModel.Empty
        ResultModel.Success(body)
    } else {
        val errorBodyString = errorBody()?.string()
        val parsedError = try {
            if (!errorBodyString.isNullOrBlank()) {
                json.decodeFromString<MonomiErrorResponse>(errorBodyString)
            } else null
        } catch (e: Exception) {
            null
        }
        ResultModel.Error(
            statusCode = code(),
            errorType  = parsedError?.errorType ?: "UNKNOWN",
            message    = parsedError?.message
        )
    }
}
