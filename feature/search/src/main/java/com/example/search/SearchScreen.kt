package com.example.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.network.model.Item


@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<SearchScreenViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is SearchScreenViewModel.UiState.Loading -> {
            LinearProgressIndicator(
                modifier = modifier.fillMaxWidth(),
            )
        }
        is SearchScreenViewModel.UiState.Success -> {
            GithubRepositoryList(
                items = (uiState as SearchScreenViewModel.UiState.Success).repositories,
                modifier = modifier,
            )
        }
        is SearchScreenViewModel.UiState.Error -> {
            // エラーメッセージを表示
            Text("エラーが発生しました")
        }
    }
}

@Composable
fun GithubRepositoryList(
    items: List<Item>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items) { item ->
            GithubRepositoryItem(item = item)
        }
    }
}

@Composable
fun GithubRepositoryItem(item: Item) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.name ?: "", style = MaterialTheme.typography.headlineLarge)
            Text(text = item.owner?.avatarUrl ?: "", style = MaterialTheme.typography.bodySmall)
            Text(text = item.language ?: "", style = MaterialTheme.typography.bodySmall)
        }
    }
}