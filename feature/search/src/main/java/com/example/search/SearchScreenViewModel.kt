package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.SearchGithubRepositoriesUseCase
import com.example.network.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel
    @Inject
    constructor(
        private val searchGithubRepositoriesUseCase: SearchGithubRepositoriesUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
        val uiState = _uiState.asStateFlow()

        private var currentPage = 1

        init {
            fetch()
        }

        fun fetch(query: String = DEFAULT_QUERY) {
            viewModelScope.launch {
                val result =
                    searchGithubRepositoriesUseCase.invoke(
                        page = currentPage,
                        perPage = PER_PAGE,
                        query = query,
                    )

                if (result.isSuccessful) {
                    val body = result.body()
                    if (body != null) {
                        _uiState.value = UiState.Success(body.items)
                    } else {
                        // / toast message
                    }
                } else {
                    _uiState.value = UiState.Error(result.code(), result.message())
                }
            }
        }

        fun refresh(query: String = DEFAULT_QUERY) {
            currentPage = 1
            fetch(query = query)
        }

        fun retry(query: String = DEFAULT_QUERY) {
            fetch(query = query)
        }

        sealed class UiState {
            data object Loading : UiState()

            data class Success(
                val repositories: List<Item>,
            ) : UiState()

            data class Error(
                val code: Int,
                val metadata: String,
            ) : UiState()
        }

        companion object {
            private const val PER_PAGE = 30
            private const val DEFAULT_QUERY = "Kotlin"
        }
    }
