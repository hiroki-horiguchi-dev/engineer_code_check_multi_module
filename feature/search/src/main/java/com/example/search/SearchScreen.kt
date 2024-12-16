package com.example.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.network.model.Item

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    onSearchItemClick: (Item) -> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var query by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.padding(innerPadding)) {
        Box(
            Modifier
                .fillMaxWidth()
                .semantics { isTraversalGroup = true },
        ) {
            SearchBar(
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .semantics { traversalIndex = 0f },
                inputField = {
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = {
                            query = it
                        },
                        onSearch = {
                            expanded = false
                            viewModel.fetch(query)
                        },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = { Text(stringResource(R.string.ex_kotlin)) },
                        leadingIcon = {
                            if (expanded && query.isEmpty()) {
                                IconButton(onClick = {
                                    expanded = !expanded
                                }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null,
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = {
                                        expanded = !expanded
                                        viewModel.fetch(query)
                                    },
                                ) {
                                    Icon(Icons.Default.Search, contentDescription = null)
                                }
                            }
                        },
                    )
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
            ) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    repeat(4) { idx ->
                        val resultText = "Suggestion $idx"
                        ListItem(
                            headlineContent = { Text(resultText) },
                            supportingContent = { Text("Additional info") },
                            leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            modifier =
                                Modifier
                                    .clickable {
                                        query = resultText
                                        expanded = false
                                        viewModel.fetch(query)
                                    }.fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                        )
                    }
                }
            }
        }

        when (uiState) {
            is SearchScreenUiState.Loading -> {
                Loading()
            }

            is SearchScreenUiState.Error -> {
                Box(
                    modifier = modifier.padding(start = 16.dp),
                ) {
                    val message = (uiState as SearchScreenUiState.Error).message
                    Text(
                        text =
                            if (message.isNotBlank()) {
                                stringResource(R.string.additional_error_msg, message)
                            } else {
                                stringResource(R.string.error_exception_msg_empty)
                            },
                    )
                }
            }

            is SearchScreenUiState.Empty -> {
                Box(
                    modifier = modifier.padding(16.dp),
                ) {
                    Text(
                        stringResource(R.string.list_empty),
                    )
                }
            }

            is SearchScreenUiState.Success -> {
                RecruitList(
                    item = (uiState as SearchScreenUiState.Success).repositories,
                    onSearchItemClick = onSearchItemClick,
                    isLoading = (uiState as SearchScreenUiState.Success).isLoading,
                    viewModel = viewModel,
                    modifier = Modifier,
                )
            }

            else -> Loading()
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun Loading() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = Color.Blue)
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun RecruitList(
    item: List<Item>,
    modifier: Modifier =
        Modifier
            .padding(top = 32.dp),
    onSearchItemClick: (Item) -> Unit,
    viewModel: SearchScreenViewModel,
    isLoading: Boolean,
) {
    val listState = rememberLazyListState()

    val isReachedToEnd by remember {
        derivedStateOf {
            val lastVisibleItemIndex =
                listState.layoutInfo.visibleItemsInfo
                    .lastOrNull()
                    ?.index
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            lastVisibleItemIndex == totalItemsCount - 1 && totalItemsCount > 0
        }
    }

    LaunchedEffect(isReachedToEnd) {
        if (isReachedToEnd) {
            viewModel.fetchNext()
        }
    }

    LazyColumn(modifier = modifier, state = listState) {
        items(item) { item ->
            Item(
                item = item,
                onSearchItemClick = onSearchItemClick,
            )
        }
        if (isLoading) {
            item {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = Color.Blue)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalGlideComposeApi::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun Item(
    item: Item,
    onSearchItemClick: (Item) -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    onSearchItemClick(item)
                },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            GlideImage(
                model = item.owner?.avatarUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = item.name ?: "",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 4.dp),
            )
            Text(
                text = item.language ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
