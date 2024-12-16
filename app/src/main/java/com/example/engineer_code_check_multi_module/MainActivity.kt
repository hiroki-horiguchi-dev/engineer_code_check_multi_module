package com.example.engineer_code_check_multi_module

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.detail.navigation.DetailRoute
import com.example.detail.navigation.detailGraph
import com.example.engineer_code_check_multi_module.ui.theme.Engineer_code_check_multi_moduleTheme
import com.example.search.navigation.SearchBaseRoute
import com.example.search.navigation.searchGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Engineer_code_check_multi_moduleTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = SearchBaseRoute.BASE_ROUTE,
                ) {
                    searchGraph { item ->
                        navController.navigate(DetailRoute.createRoute(name = item.name ?: "nothing"))
                    }
                    detailGraph(navController)
                }
            }
        }
    }
}
