package com.example.monomi.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monomi.R
import com.example.monomi.core.model.SearchItem
import com.example.monomi.presentation.theme.MonomiTheme
import com.example.monomi.presentation.util.PreviewUtil

@Composable
fun ItemCard(
    item: SearchItem,
    showBookmarkButton: Boolean = true,
    onBookmark: () -> Unit
) {
    Card {
        Row(Modifier.padding(8.dp)) {
            NetworkImage(
                modifier = Modifier.size(width = 120.dp, height = 80.dp)
                    .clip(RoundedCornerShape(10.dp)),
                imageUrl = item.thumbnailUrl,
                placeholder = painterResource(R.drawable.ic_launcher_background)
            )
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                Text(item.dateTime.take(10))
                Text(item.type.name)
            }
            if (showBookmarkButton) {
                IconButton(onClick = onBookmark) {
                    Icon(
                        imageVector = if (item.isBookmarked) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchItemCardPreview() {
    MonomiTheme {
        ItemCard(
            item = PreviewUtil.mockSearchItem(true),
            onBookmark = {}
        )
    }
}
