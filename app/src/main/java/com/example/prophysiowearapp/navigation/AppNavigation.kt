package com.example.prophysiowearapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.prophysiowearapp.ui.screens.AppointmentListScreen
import com.example.prophysiowearapp.ui.screens.ConfirmedAppointmentScreen
import com.example.prophysiowearapp.ui.screens.ReminderScreen
import com.example.prophysiowearapp.ui.screens.CanceledAppointmentScreen

sealed class Screen(val route: String) {
    object AppointmentList : Screen("appointment_list")
    object ConfirmedAppointment : Screen("confirmed_appointment")
    object Reminder : Screen("reminder")
    object CanceledAppointment : Screen("canceled_appointment")
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.AppointmentList.route
    ) {
        composable(Screen.AppointmentList.route) {
            AppointmentListScreen(navController = navController)
        }
        composable(Screen.ConfirmedAppointment.route) {
            ConfirmedAppointmentScreen(navController = navController)
        }
        composable(Screen.Reminder.route) {
            ReminderScreen(navController = navController)
        }
        composable(Screen.CanceledAppointment.route) {
            CanceledAppointmentScreen(navController = navController)
        }
    }
}
