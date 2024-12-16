package com.example.domain.di

import com.example.domain.usecase.SearchGithubRepositoriesUseCase
import com.example.domain.usecase.SearchGithubRepositoriesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchGithubRepositoriesUseCaseModule {
    @Binds
    @Singleton
    abstract fun bindSearchGithubRepositoriesUseCase(
        searchGithubRepositoriesUseCaseImpl: SearchGithubRepositoriesUseCaseImpl,
    ): SearchGithubRepositoriesUseCase
}
