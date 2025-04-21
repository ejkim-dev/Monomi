package com.example.monomi.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.monomi.ui.feature.bookmark.BookmarkScreen
import com.example.monomi.ui.feature.search.SearchScreen

@Composable
fun MonomiNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController, startDestination = "search") {
        composable("search") {
            SearchScreen(
                navigateToBookmark = { navController.navigate("bookmark") },
                onExitConfirm = { (context as? Activity)?.finishAffinity() }
            )
        }
        composable("bookmark") {
            BookmarkScreen(navigateBack = { navController.popBackStack() })
        }
    }
}