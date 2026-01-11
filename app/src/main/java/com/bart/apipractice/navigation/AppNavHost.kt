package com.bart.apipractice.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bart.apipractice.ui.compose.users.UserDetailScreen
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

        composable(
            route = "${NavRoutes.USER_DETAIL}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            UserDetailScreen(
                userId = userId,
                onBackClick = { navController.popBackStack() } // <--- Use popBackStack
            )
        }

    }

}
