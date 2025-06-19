package com.example.prophysiowearapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Info
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
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.example.prophysiowearapp.ui.theme.Blue
import com.example.prophysiowearapp.ui.theme.Green
import com.example.prophysiowearapp.utils.getResponsivePadding
import com.example.prophysiowearapp.utils.rememberWearScreenSize
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun ServiceDetailScreen(
    navController: NavController,
    serviceName: String,
    serviceDescription: String,
    servicePrice: String
) {
    val screenSize = rememberWearScreenSize()
    val responsivePadding = getResponsivePadding(screenSize)

    // Decodificar los parámetros URL
    val decodedName = URLDecoder.decode(serviceName, StandardCharsets.UTF_8.toString())
    val decodedDescription = URLDecoder.decode(serviceDescription, StandardCharsets.UTF_8.toString())
    val decodedPrice = URLDecoder.decode(servicePrice, StandardCharsets.UTF_8.toString())

    val (icon, color) = getServiceIconAndColor(decodedName)

    Scaffold {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = responsivePadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Header del servicio
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = responsivePadding * 2)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.radialGradient(
                                    colors = listOf(color, color.copy(alpha = 0.7f))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = decodedName,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Card de descripción
            if (decodedDescription.isNotEmpty()) {
                item {
                    Card(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Blue,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "Descripción",
                                    style = MaterialTheme.typography.caption1,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = decodedDescription,
                                style = MaterialTheme.typography.caption2,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
                }
            }

            // Card de precio
            if (decodedPrice.isNotEmpty()) {
                item {
                    Card(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AttachMoney,
                                    contentDescription = null,
                                    tint = Green,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "Precio",
                                    style = MaterialTheme.typography.caption1,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = "$$decodedPrice",
                                style = MaterialTheme.typography.body1,
                                color = Green,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            // Botón de regreso
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "Volver",
                            style = MaterialTheme.typography.caption1
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(responsivePadding * 2))
            }
        }
    }
}
