package com.example.monomi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.monomi.presentation.bookmark.BookmarkScreen
import com.example.monomi.presentation.search.SearchScreen

@Composable
fun MonomiNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "search") {
        composable("search") {
            SearchScreen(navigateToBookmark = { navController.navigate("bookmark") })
        }
        composable("bookmark") {
            BookmarkScreen(navigateBack = { navController.popBackStack() })
        }
    }
}