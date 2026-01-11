package com.bart.apipractice.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bart.apipractice.ui.compose.users.UserScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.USER_LIST) {
        composable(route = NavRoutes.USER_LIST) {
            UserScreen(onUserClick = {
                userId ->
                navController.navigate("${NavRoutes.USER_DETAIL}/$userId")
            })
        }
    }

}
