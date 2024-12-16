package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.SearchGithubRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("ktlint:standard:backing-property-naming")
@HiltViewModel
class SearchScreenViewModel
    @Inject
    constructor(
        private val searchGithubRepositoriesUseCase: SearchGithubRepositoriesUseCase,
    ) : ViewModel() {
        internal var query = DEFAULT_QUERY
        internal var isLastPage = false

        internal val _uiState = MutableStateFlow<SearchScreenUiState>(SearchScreenUiState.Loading)
        internal val uiState = _uiState.asStateFlow()

        private var currentPage = 1

        init {
            fetch(DEFAULT_QUERY)
        }

        fun fetch(query: String) {
            this.query = query
            viewModelScope.launch {
                val result =
                    searchGithubRepositoriesUseCase.invoke(
                        page = currentPage,
                        perPage = PER_PAGE,
                        query = query,
                    )

                if (result.isSuccessful) {
                    val items = result.body()?.items
                    if (items.isNullOrEmpty()) {
                        _uiState.value = SearchScreenUiState.Empty
                        isLastPage = true
                    } else {
                        _uiState.value = SearchScreenUiState.Success(items)
                        currentPage++
                    }
                } else {
                    _uiState.value = SearchScreenUiState.Error(result.code(), result.message())
                }
            }
        }

        fun fetchNext() {
            if (isLastPage) return

            _uiState.value =
                (_uiState.value as? SearchScreenUiState.Success)?.copy(isLoading = true)
                    ?: SearchScreenUiState.Loading
            viewModelScope.launch {
                val result =
                    searchGithubRepositoriesUseCase.invoke(
                        page = currentPage,
                        perPage = PER_PAGE,
                        query = query,
                    )

                if (result.isSuccessful) {
                    val items = result.body()?.items
                    if (items.isNullOrEmpty()) {
                        _uiState.value = SearchScreenUiState.Empty
                        isLastPage = true
                    } else {
                        _uiState.value = SearchScreenUiState.Success(items)
                        currentPage++
                    }
                } else {
                    _uiState.value = SearchScreenUiState.Error(result.code(), result.message())
                }
            }
        }

        companion object {
            private const val PER_PAGE = 30
            private const val DEFAULT_QUERY = "Kotlin"
        }
    }
