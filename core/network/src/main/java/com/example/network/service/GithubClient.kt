package com.example.network.service

import com.example.network.model.GithubResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


class GithubClient @Inject constructor(
    private val githubService: GithubService
) {

    suspend fun fetchGithubRepositoryList(searchText: String, page: Int, perPage: Int): Response<GithubResponse> =
        githubService.fetchGithubRepositoryList(
            accept = ACCEPT,
            page = page,
            perPage = perPage,
            searchText = searchText
        )

    companion object {
        private const val ACCEPT = "application/vnd.github.v3+json"
    }
}