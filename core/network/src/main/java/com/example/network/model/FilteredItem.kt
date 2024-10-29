package com.example.network.model

data class FilteredItem(
    val name: String,
    val owner: Owner?,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
    var isFavorite: Boolean
)