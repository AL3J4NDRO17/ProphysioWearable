package com.example.prophysiowearapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.prophysiowearapp.model.Appointment
import com.example.prophysiowearapp.model.AppointmentStatus
import com.example.prophysiowearapp.ui.theme.Blue
import com.example.prophysiowearapp.ui.theme.Orange
import com.example.prophysiowearapp.ui.theme.Red
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AppointmentCard(
    appointment: Appointment,
    onClick: () -> Unit
) {
    val (backgroundColor, icon, title, timeText) = when (appointment.status) {
        AppointmentStatus.CONFIRMED -> {
            val timeText = if (appointment.dateTime.toLocalDate() == LocalDateTime.now().toLocalDate()) {
                "Hoy ${appointment.dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"
            } else {
                "Mañana ${appointment.dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"
            }
            Quadruple(Blue, Icons.Default.CheckCircle, "Cita confirmada", timeText)
        }
        AppointmentStatus.REMINDER -> {
            val timeText = if (appointment.dateTime.toLocalDate() == LocalDateTime.now().toLocalDate()) {
                "En 1 hora"
            } else {
                "Mañana ${appointment.dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"
            }
            Quadruple(Orange, Icons.Default.Notifications, "Recordatorio", timeText)
        }
        AppointmentStatus.CANCELED -> {
            val timeText = "Hoy ${appointment.dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"
            Quadruple(Red, Icons.Default.Cancel, "Cita cancelada", timeText)
        }
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(color = backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = timeText,
                    style = MaterialTheme.typography.caption2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
