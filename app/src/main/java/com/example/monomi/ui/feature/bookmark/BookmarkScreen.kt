package com.example.monomi.ui.feature.bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.monomi.ui.component.SearchItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    navigateBack: () -> Unit,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val list by viewModel.bookmarks.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("보관함") },
                navigationIcon = { IconButton(onClick = navigateBack) {
                    Icon(Icons.Default.ArrowBack, "뒤로 가기")
                }}
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(list) { item ->
                SearchItemCard(item, false) { }
                Spacer(Modifier.height(6.dp))
            }
        }
    }
}