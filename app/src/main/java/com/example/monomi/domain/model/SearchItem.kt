package com.example.monomi.domain.model

data class SearchItem(
    val id: String,               // bookmark 구분용
    val thumbnailUrl: String,
    val linkUrl: String,
    val datetime: String,
    val type: Type,
    var isBookmarked: Boolean = false
) {
    enum class Type { IMAGE, VIDEO }
}