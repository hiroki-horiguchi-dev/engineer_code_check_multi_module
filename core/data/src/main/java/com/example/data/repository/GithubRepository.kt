package com.example.data.repository

import com.example.network.model.GithubResponse
import com.example.network.service.GithubClient
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


interface GithubRepository {
    suspend fun searchGithubRepository(
        page: Int,
        perPage: Int,
        searchText: String
    ): Flow<Response<GithubResponse>>
}

class GithubRepositoryImpl @Inject constructor(
    private val githubClient: GithubClient
) : GithubRepository {

    override suspend fun searchGithubRepository(
        page: Int,
        perPage: Int,
        searchText: String
    ): Flow<Response<GithubResponse>> =
        githubClient.fetchGithubRepositoryList(
            page = page,
            perPage = perPage,
            searchText = searchText
        )
}