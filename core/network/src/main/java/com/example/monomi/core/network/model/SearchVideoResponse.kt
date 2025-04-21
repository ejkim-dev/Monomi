package com.example.monomi.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchVideoResponse(
    val documents: List<VideoDocument>,
    val meta: Meta
)

@Serializable
data class VideoDocument(
    val thumbnail: String,
    val url: String,
    val datetime: String,
    val title: String,
    val author: String
)