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
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.example.prophysiowearapp.ui.components.ResponsiveMenuCard
import com.example.prophysiowearapp.ui.theme.Blue
import com.example.prophysiowearapp.ui.theme.Green
import com.example.prophysiowearapp.ui.theme.Orange
import com.example.prophysiowearapp.utils.IntentUtils
import com.example.prophysiowearapp.utils.rememberWearScreenSize
import com.example.prophysiowearapp.viewmodel.AboutViewModel

@Composable
fun AboutScreen(
    navController: NavController,
    viewModel: AboutViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val screenSize = rememberWearScreenSize()
    val clinicInfo by viewModel.clinicInfo.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Header
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
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

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = clinicInfo?.name ?: "ProPhysio",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = clinicInfo?.address ?: "Clínica de Fisioterapia",
                        style = MaterialTheme.typography.caption2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (isLoading) {
                item {
                    CircularProgressIndicator()
                    Text("Cargando información...", style = MaterialTheme.typography.caption2)
                }
            } else if (error != null) {
                item {
                    Text("Error: ${error}", color = MaterialTheme.colors.error)
                    // Opcional: botón para reintentar
                }
            } else if (clinicInfo != null) {
                val info = clinicInfo!!

                // Solo mostrar ubicación si tenemos coordenadas válidas
                if (info.latitude != null && info.longitude != null &&
                    info.latitude != 0.0 && info.longitude != 0.0) {
                    item {
                        ResponsiveMenuCard(
                            title = "Ubicación",
                            subtitle = "Ver en Maps",
                            icon = Icons.Default.LocationOn,
                            backgroundColor = Blue,
                            screenSize = screenSize,
                            onClick = {
                                IntentUtils.openGoogleMaps(
                                    context = context,
                                    latitude = info.latitude,
                                    longitude = info.longitude,
                                    label = info.name ?: "ProPhysio"
                                )
                            }
                        )
                    }
                }

                // Solo mostrar WhatsApp si tenemos número
                if (!info.whatsappPhone.isNullOrBlank()) {
                    item {
                        ResponsiveMenuCard(
                            title = "WhatsApp",
                            subtitle = "Contactar",
                            icon = Icons.Default.Phone,
                            backgroundColor = Green,
                            screenSize = screenSize,
                            onClick = {
                                IntentUtils.openWhatsApp(
                                    context = context,
                                    phoneNumber = info.whatsappPhone,
                                    message = "Hola, me interesa información sobre sus servicios de fisioterapia"
                                )
                            }
                        )
                    }
                }

                // Solo mostrar Instagram si tenemos usuario
                if (!info.instagramUser.isNullOrBlank()) {
                    item {
                        ResponsiveMenuCard(
                            title = "Instagram",
                            subtitle = "@${info.instagramUser}",
                            icon = Icons.Default.Public,
                            backgroundColor = Orange,
                            screenSize = screenSize,
                            onClick = {
                                IntentUtils.openInstagram(
                                    context = context,
                                    username = info.instagramUser
                                )
                            }
                        )
                    }
                }

                // Solo mostrar Facebook si tenemos pageId
                if (!info.facebookPageId.isNullOrBlank()) {
                    item {
                        ResponsiveMenuCard(
                            title = "Facebook",
                            subtitle = "Síguenos",
                            icon = Icons.Default.Facebook,
                            backgroundColor = MaterialTheme.colors.primary,
                            screenSize = screenSize,
                            onClick = {
                                IntentUtils.openFacebook(
                                    context = context,
                                    pageId = info.facebookPageId
                                )
                            }
                        )
                    }
                }
            } else {
                item {
                    Text("No se pudo cargar la información de la clínica.", style = MaterialTheme.typography.caption2)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "© 2024 ProPhysio",
                        style = MaterialTheme.typography.caption2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Versión 1.0",
                        style = MaterialTheme.typography.caption2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
