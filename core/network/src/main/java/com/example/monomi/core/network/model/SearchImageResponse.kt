package com.example.monomi.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchImageResponse(
    val documents: List<ImageDocument>,
    val meta: Meta
)

@Serializable
data class ImageDocument(
    @SerialName("thumbnail_url") val thumbnailUrl: String,
    @SerialName("display_sitename") val displayName: String,
    @SerialName("image_url") val imageUrl: String,
    val datetime: String,
    @SerialName("doc_url") val docUrl: String
)