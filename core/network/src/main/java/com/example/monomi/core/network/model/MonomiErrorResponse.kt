package com.example.monomi.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MonomiErrorResponse(
    val errorType: String,
    val message: String
)
