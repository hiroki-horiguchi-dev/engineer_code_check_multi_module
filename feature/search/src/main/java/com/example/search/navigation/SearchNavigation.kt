package com.example.search.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.network.model.Item
import com.example.search.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute {
    const val ROUTE = "search"
}

@Serializable
data object SearchBaseRoute {
    const val BASE_ROUTE = "search_base"
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.searchGraph(onSearchItemClick: (Item) -> Unit) {
    navigation(
        route = SearchBaseRoute.BASE_ROUTE,
        startDestination = SearchRoute.ROUTE,
    ) {
        composable(route = SearchRoute.ROUTE) {
            Scaffold(
                topBar = {
                    // SearchScreen 用の TopAppBar
                    TopAppBar(title = {
                        Text(
                            text = "Search",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    })
                },
            ) { innerPadding ->
                SearchScreen(
                    onSearchItemClick = onSearchItemClick,
                    innerPadding = innerPadding,
                )
            }
        }
    }
}
