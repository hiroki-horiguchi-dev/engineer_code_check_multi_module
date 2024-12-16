package com.example.detail.navigation

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.detail.DetailScreen
import com.example.network.model.Item
import kotlinx.serialization.Serializable

@Serializable
object DetailRoute {
    const val ROUTE = "detail"
    const val ROUTE_WITH_ARGS = "$ROUTE/{name}/{avatarUrl}"

    fun createRoute(item: Item) = "$ROUTE/${Uri.encode(item.name)}/${Uri.encode(item.owner?.avatarUrl ?: "")}"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.detailGraph(navController: NavHostController) {
    composable(
        route = DetailRoute.ROUTE_WITH_ARGS,
        arguments =
            listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("avatarUrl") { type = NavType.StringType },
            ),
    ) { backStackEntry ->
        val name = backStackEntry.arguments?.getString("name") ?: ""
        val avatarUrl = backStackEntry.arguments?.getString("avatarUrl") ?: ""
        Scaffold(
            topBar = {
                // DetailScreen 用の TopAppBar
                TopAppBar(
                    title = {
                        Text(
                            text = "Detail",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "戻る")
                        }
                    },
                )
            },
        ) { innerPadding ->
            DetailScreen(
                name = name,
                avatarUrl = avatarUrl,
                innerPadding = innerPadding,
                modifier = Modifier,
            )
        }
    }
}
