package com.example.domain.di

import com.example.domain.usecase.SearchGithubRepositoriesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule() {

    @Binds
    @Singleton
    abstract fun bindSearchGithubRepositoriesUseCase(
        searchGithubRepositoriesUseCase: SearchGithubRepositoriesUseCase
    ): SearchGithubRepositoriesUseCase
}