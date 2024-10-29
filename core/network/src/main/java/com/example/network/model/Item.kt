package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName(value = "full_name")
    val name: String?,
    val owner: Owner?,
    val language: String?,
    @SerialName("stargazers_count")
    val stargazersCount: Long?,
    @SerialName(value = "watchers_count")
    val watchersCount: Long?,
    @SerialName(value = "forks_count")
    val forksCount: Long?,
    @SerialName(value = "open_issues_count")
    val openIssuesCount: Long?
)

@Serializable
data class Owner(
    @SerialName(value = "avatar_url")
    val avatarUrl: String
)