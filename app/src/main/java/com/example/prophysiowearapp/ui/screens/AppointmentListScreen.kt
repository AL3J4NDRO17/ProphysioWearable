package com.example.prophysiowearapp.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Scaffold
import com.example.prophysiowearapp.model.Appointment
import com.example.prophysiowearapp.model.AppointmentStatus
import com.example.prophysiowearapp.navigation.Screen
import com.example.prophysiowearapp.ui.components.AppointmentCard
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun AppointmentListScreen(navController: NavController) {
    // Datos de ejemplo
    val appointments = listOf(
        Appointment(
            id = "1",
            doctor = "Dr. García",
            room = "Sala 2",
            dateTime = LocalDateTime.now().plus(1, ChronoUnit.DAYS).withHour(10).withMinute(0),
            status = AppointmentStatus.CONFIRMED
        ),
        Appointment(
            id = "2",
            doctor = "Dr. García",
            room = "Sala 2",
            dateTime = LocalDateTime.now().plus(1, ChronoUnit.HOURS),
            description = "Terapia de espalda",
            status = AppointmentStatus.REMINDER
        ),
        Appointment(
            id = "3",
            doctor = "Dr. García",
            room = "Sala 2",
            dateTime = LocalDateTime.now().withHour(15).withMinute(30),
            status = AppointmentStatus.CANCELED
        )
    )

    Scaffold {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            items(appointments) { appointment ->
                AppointmentCard(
                    appointment = appointment,
                    onClick = {
                        when (appointment.status) {
                            AppointmentStatus.CONFIRMED -> navController.navigate(Screen.ConfirmedAppointment.route)
                            AppointmentStatus.REMINDER -> navController.navigate(Screen.Reminder.route)
                            AppointmentStatus.CANCELED -> navController.navigate(Screen.CanceledAppointment.route)
                        }
                    }
                )
            }
        }
    }
}
