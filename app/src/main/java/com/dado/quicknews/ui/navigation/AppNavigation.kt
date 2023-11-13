package com.dado.quicknews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dado.quicknews.ui.screens.HomeScreen

@Composable
fun AppNavigationGraph() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.HOME_SCREEN ) {

        composable(Screens.HOME_SCREEN) {
            HomeScreen()
        }
    }
}
