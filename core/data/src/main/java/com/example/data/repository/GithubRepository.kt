package com.example.data.repository

import com.example.network.model.GithubResponse
import com.example.network.service.GithubClient
import retrofit2.Response
import javax.inject.Inject

interface GithubRepository {
    suspend fun searchGithubRepository(
        page: Int,
        perPage: Int,
        searchText: String,
    ): Response<GithubResponse>
}

class GithubRepositoryImpl
    @Inject
    constructor(
        private val githubClient: GithubClient,
    ) : GithubRepository {
        override suspend fun searchGithubRepository(
            page: Int,
            perPage: Int,
            searchText: String,
        ): Response<GithubResponse> =
            githubClient.fetchGithubRepositoryList(
                page = page,
                perPage = perPage,
                searchText = searchText,
            )
    }
