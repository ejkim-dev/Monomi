package com.example.monomi.ui.feature.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.monomi.ui.component.SearchItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navigateToBookmark: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monomi Search") },
                actions = {
                    OutlinedButton(
                        onClick = navigateToBookmark,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text("Show Bookmark")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            OutlinedTextField(
                value = uiState.query,
                onValueChange = { viewModel.sendIntent(SearchIntent.TextChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("검색어 입력") }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                itemsIndexed(uiState.items) { index, item ->
                    if (index == uiState.items.lastIndex - 5) {
                        viewModel.sendIntent(SearchIntent.LoadNextPage)
                    }
                    SearchItemCard(item) {
                        viewModel.sendIntent(SearchIntent.ToggleBookmark(item))
                    }
                    Spacer(Modifier.height(6.dp))
                }

                if (uiState.isLoading) {
                    item { CircularProgressIndicator(Modifier.padding(16.dp)) }
                }
            }
        }
    }
}
