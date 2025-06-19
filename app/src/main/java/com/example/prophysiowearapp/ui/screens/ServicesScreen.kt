package com.example.prophysiowearapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.example.prophysiowearapp.navigation.Screen
import com.example.prophysiowearapp.network.ServiceDto
import com.example.prophysiowearapp.ui.theme.Blue
import com.example.prophysiowearapp.ui.theme.Green
import com.example.prophysiowearapp.ui.theme.Orange
import com.example.prophysiowearapp.ui.theme.Purple200
import com.example.prophysiowearapp.ui.theme.Red
import com.example.prophysiowearapp.ui.theme.Turquess
import com.example.prophysiowearapp.utils.getResponsivePadding
import com.example.prophysiowearapp.utils.rememberWearScreenSize
import com.example.prophysiowearapp.viewmodel.ServicesViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ServicesScreen(
    navController: NavController,
    viewModel: ServicesViewModel = hiltViewModel()
) {
    val screenSize = rememberWearScreenSize()
    val responsivePadding = getResponsivePadding(screenSize)
    val services by viewModel.services.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = responsivePadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Header con diseño atractivo
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = responsivePadding * 2)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                    colors = listOf(Blue, Turquess)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MedicalServices,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Nuestros Servicios",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Especialidades médicas",
                        style = MaterialTheme.typography.caption2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (isLoading) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 24.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            strokeWidth = 3.dp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Cargando servicios...",
                            style = MaterialTheme.typography.caption2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            } else if (error != null) {
                item {
                    ErrorCard(
                        message = "Error al cargar servicios",
                        onRetry = { viewModel.fetchServices() }
                    )
                }
            } else if (services.isNotEmpty()) {
                items(services) { service ->
                    ServiceCard(
                        service = service,
                        screenSize = screenSize,
                        onClick = {
                            // Navegar a detalle del servicio
                            val encodedName = URLEncoder.encode(service.name, StandardCharsets.UTF_8.toString())
                            val encodedDescription = URLEncoder.encode(service.description ?: "", StandardCharsets.UTF_8.toString())
                            val encodedPrice = URLEncoder.encode(service.price ?: "", StandardCharsets.UTF_8.toString())

                            navController.navigate(
                                Screen.ServiceDetail.createRoute(encodedName, encodedDescription, encodedPrice)
                            )
                        }
                    )
                }
            } else {
                item {
                    EmptyStateCard()
                }
            }

            item {
                Spacer(modifier = Modifier.height(responsivePadding * 2))
            }
        }
    }
}

@Composable
fun ServiceCard(
    service: ServiceDto,
    screenSize: com.example.prophysiowearapp.utils.WearScreenSize,
    onClick: () -> Unit
) {
    val (icon, color) = getServiceIconAndColor(service.name)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono del servicio
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // SOLO EL NOMBRE DEL SERVICIO - SIN CORTAR
            Text(
                text = service.name,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
                // ✅ Sin maxLines ni overflow - el texto se adapta naturalmente
            )

            // Flecha para indicar que es clickeable
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun ErrorCard(
    message: String,
    onRetry: () -> Unit
) {
    Card(
        onClick = onRetry,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MedicalServices,
                contentDescription = null,
                tint = Red,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.caption1,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Toca para reintentar",
                style = MaterialTheme.typography.caption2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EmptyStateCard() {
    Card(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MedicalServices,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "No hay servicios disponibles",
                style = MaterialTheme.typography.caption1,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = "Consulte más tarde",
                style = MaterialTheme.typography.caption2,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

fun getServiceIconAndColor(serviceName: String): Pair<ImageVector, Color> {
    return when {
        serviceName.contains("consulta", ignoreCase = true) ||
                serviceName.contains("médica", ignoreCase = true) ->
            Icons.Default.LocalHospital to Blue

        serviceName.contains("terapia", ignoreCase = true) ||
                serviceName.contains("física", ignoreCase = true) ||
                serviceName.contains("fisio", ignoreCase = true) ->
            Icons.Default.Healing to Green

        serviceName.contains("nutricional", ignoreCase = true) ||
                serviceName.contains("alimentic", ignoreCase = true) ->
            Icons.Default.Restaurant to Orange

        serviceName.contains("postoperatoria", ignoreCase = true) ||
                serviceName.contains("cirugía", ignoreCase = true) ||
                serviceName.contains("revisión", ignoreCase = true) ->
            Icons.Default.MonitorHeart to Red

        serviceName.contains("psicolog", ignoreCase = true) ||
                serviceName.contains("mental", ignoreCase = true) ->
            Icons.Default.Psychology to Purple200

        else -> Icons.Default.MedicalServices to Turquess
    }
}
