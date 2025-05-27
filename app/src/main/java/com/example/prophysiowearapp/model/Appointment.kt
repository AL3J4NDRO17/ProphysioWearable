package com.example.prophysiowearapp.model

import java.time.LocalDateTime

data class Appointment(
    val id: String,
    val doctor: String,
    val room: String,
    val dateTime: LocalDateTime,
    val type: String = "Fisioterapia",
    val description: String = "",
    val status: AppointmentStatus
)

enum class AppointmentStatus {
    CONFIRMED,
    REMINDER,
    CANCELED
}
