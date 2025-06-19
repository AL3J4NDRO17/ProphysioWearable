package com.example.prophysiowearapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.prophysiowearapp.ui.screens.AboutScreen
import com.example.prophysiowearapp.ui.screens.MainMenuScreen
import com.example.prophysiowearapp.ui.screens.ScheduleScreen
import com.example.prophysiowearapp.ui.screens.ServiceDetailScreen
import com.example.prophysiowearapp.ui.screens.ServicesScreen

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object About : Screen("about")
    object Schedule : Screen("schedule")
    object Services : Screen("services")
    object ServiceDetail : Screen("service_detail/{serviceName}/{serviceDescription}/{servicePrice}") {
        fun createRoute(serviceName: String, serviceDescription: String, servicePrice: String) =
            "service_detail/$serviceName/$serviceDescription/$servicePrice"
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainMenu.route
    ) {
        composable(Screen.MainMenu.route) {
            MainMenuScreen(navController = navController)
        }
        composable(Screen.About.route) {
            AboutScreen(navController = navController)
        }
        composable(Screen.Schedule.route) {
            ScheduleScreen(navController = navController)
        }
        composable(Screen.Services.route) {
            ServicesScreen(navController = navController)
        }
        composable(Screen.ServiceDetail.route) { backStackEntry ->
            val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
            val serviceDescription = backStackEntry.arguments?.getString("serviceDescription") ?: ""
            val servicePrice = backStackEntry.arguments?.getString("servicePrice") ?: ""

            ServiceDetailScreen(
                navController = navController,
                serviceName = serviceName,
                serviceDescription = serviceDescription,
                servicePrice = servicePrice
            )
        }
    }
}
