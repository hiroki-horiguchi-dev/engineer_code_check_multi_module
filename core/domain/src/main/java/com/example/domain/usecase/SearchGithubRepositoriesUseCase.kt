package com.example.domain.usecase

import com.example.data.repository.GithubRepository
import com.example.network.model.GithubResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

interface SearchGithubRepositoriesUseCase {
    suspend operator fun invoke(
        page: Int,
        perPage: Int,
        query: String
    ): Flow<Response<GithubResponse>>
}

class SearchGithubRepositoriesUseCaseImpl @Inject constructor(
    private val githubRepository: GithubRepository
) : SearchGithubRepositoriesUseCase {

    override suspend operator fun invoke(page: Int, perPage: Int,query: String): Flow<Response<GithubResponse>> =
        githubRepository.searchGithubRepository(page, perPage, query)
}