package com.example.monomi.core.data.mapper

import com.example.monomi.core.model.SearchItem
import com.example.monomi.core.network.model.SearchImageResponse
import com.example.monomi.core.network.model.SearchVideoResponse

fun mapImageResponseToItems(response: SearchImageResponse): List<SearchItem> =
    response.documents.map { doc ->
        SearchItem(
            id           = doc.imageUrl.hashCode().toString(),
            thumbnailUrl = doc.thumbnailUrl,
            linkUrl      = doc.docUrl,
            dateTime     = doc.datetime,
            type         = SearchItem.Type.IMAGE
        )
    }


fun mapVideoResponseToItems(response: SearchVideoResponse): List<SearchItem> =
    response.documents.map { doc ->
        SearchItem(
            id           = doc.thumbnail.hashCode().toString(),
            thumbnailUrl = doc.thumbnail,
            linkUrl      = doc.url,
            dateTime     = doc.datetime,
            type         = SearchItem.Type.VIDEO
        )
    }