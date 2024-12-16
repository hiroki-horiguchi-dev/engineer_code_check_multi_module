package com.example.search

import com.example.network.model.Item

sealed class SearchScreenUiState {
    data object Loading : SearchScreenUiState()

    data object Empty : SearchScreenUiState()

    data class Success(
        val repositories: List<Item>,
        val isLoading: Boolean = false,
    ) : SearchScreenUiState()

    data class Error(
        val code: Int,
        val message: String,
    ) : SearchScreenUiState()
}
