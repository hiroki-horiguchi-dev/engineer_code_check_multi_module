package com.example.network.service

import com.example.network.model.GithubResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories")
    suspend fun fetchGithubRepositoryList(
        @Header("Accept") accept: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("q") searchText: String
    ): Response<GithubResponse>
}