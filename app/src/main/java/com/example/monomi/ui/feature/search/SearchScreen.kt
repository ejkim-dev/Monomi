package com.example.monomi.ui.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.monomi.core.model.SearchItem
import com.example.monomi.ui.component.ExitConfirmationDialog
import com.example.monomi.ui.component.SearchItemCard

@Composable
fun SearchScreen(
    navigateToBookmark: () -> Unit,
    onExitConfirm: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()

    // 뒤로가기 확인
    var showExitDialog by remember { mutableStateOf(false) }
    BackHandler { showExitDialog = true }

    // 종료 확인
    if (showExitDialog) {
        ExitConfirmationDialog(
            onDismiss = { showExitDialog = false },
            onConfirm = onExitConfirm
        )
    }

    Scaffold(
        topBar = { SearchTopBar(navigateToBookmark) }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            SearchBar(
                query = uiState.query,
                onQueryChange = { viewModel.sendIntent(SearchIntent.TextChanged(it)) },
                onSearch = { viewModel.sendIntent(SearchIntent.Search) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            SearchResults(
                items = uiState.items,
                isLoading = uiState.isLoading,
                onLoadNextPage = { viewModel.sendIntent(SearchIntent.LoadNextPage) },
                onToggleBookmark = { viewModel.sendIntent(SearchIntent.ToggleBookmark(it)) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopBar(
    navigateToBookmark: () -> Unit
) {
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

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        label = { Text("검색어 입력") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                focusManager.clearFocus()
            }
        ),
        maxLines = 1
    )
}

@Composable
private fun SearchResults(
    items: List<SearchItem>,
    isLoading: Boolean,
    onLoadNextPage: () -> Unit,
    onToggleBookmark: (SearchItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (items.isEmpty() && !isLoading) {
            // 검색 결과가 없는 경우
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "검색 결과가 없습니다",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // 검색 결과가 있는 경우
            LazyColumn(
                contentPadding = PaddingValues(8.dp)
            ) {
                itemsIndexed(items) { index, item ->
                    if (index == items.lastIndex - 5) {
                        onLoadNextPage()
                    }
                    SearchItemCard(item) { onToggleBookmark(item) }
                    Spacer(Modifier.height(6.dp))
                }

                if (isLoading && items.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

        // 첫 로딩 시 중앙에 표시되는 로딩 인디케이터
        if (isLoading && items.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
