package com.example.prophysiowearapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.example.prophysiowearapp.navigation.Screen
import com.example.prophysiowearapp.ui.components.ResponsiveMenuCard
import com.example.prophysiowearapp.ui.theme.Blue
import com.example.prophysiowearapp.ui.theme.Green
import com.example.prophysiowearapp.ui.theme.Turquess
import com.example.prophysiowearapp.utils.getResponsivePadding
import com.example.prophysiowearapp.utils.rememberWearScreenSize

@Composable
fun MainMenuScreen(navController: NavController) {
    val screenSize = rememberWearScreenSize()
    val surfaceColor = MaterialTheme.colors.surface
    val responsivePadding = getResponsivePadding(screenSize)

    Scaffold {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = responsivePadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Header responsivo
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = responsivePadding * 3)
                ) {
                    Box(
                        modifier = Modifier
                            .size(
                                when (screenSize.size) {
                                    com.example.prophysiowearapp.utils.ScreenSize.SMALL -> 48.dp
                                    com.example.prophysiowearapp.utils.ScreenSize.MEDIUM -> 56.dp
                                    com.example.prophysiowearapp.utils.ScreenSize.LARGE -> 64.dp
                                }
                            )
                            .clip(CircleShape)
                            .background(color = Blue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "PF",
                            color = Color.White,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(responsivePadding * 2))

                    Text(
                        text = "ProPhysio",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Clínica de Fisioterapia",
                        style = MaterialTheme.typography.caption2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Menú principal - solo 3 opciones
            val menuItems = listOf(
                SimpleMenuItem(
                    title = "Servicios",
                    subtitle = "Nuestras especialidades",
                    icon = Icons.Default.MedicalServices,
                    color = Turquess,
                    onClick = { navController.navigate(Screen.Services.route) }
                ),
                SimpleMenuItem(
                    title = "Horarios",
                    subtitle = "Horarios de atención",
                    icon = Icons.Default.AccessTime,
                    color = Green,
                    onClick = { navController.navigate(Screen.Schedule.route) }
                ),
                SimpleMenuItem(
                    title = "Acerca de",
                    subtitle = "Información y contacto",
                    icon = Icons.Default.Info,
                    color = surfaceColor,
                    onClick = { navController.navigate(Screen.About.route) }
                )
            )

            items(menuItems) { menuItem ->
                ResponsiveMenuCard(
                    title = menuItem.title,
                    subtitle = menuItem.subtitle,
                    icon = menuItem.icon,
                    backgroundColor = menuItem.color,
                    screenSize = screenSize,
                    onClick = menuItem.onClick
                )
            }

            item {
                Spacer(modifier = Modifier.height(responsivePadding * 3))
                Text(
                    text = "v1.0",
                    style = MaterialTheme.typography.caption2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

data class SimpleMenuItem(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: androidx.compose.ui.graphics.Color,
    val onClick: () -> Unit
)
