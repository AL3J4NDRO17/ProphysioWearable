package com.example.prophysiowearapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.prophysiowearapp.utils.WearScreenSize
import com.example.prophysiowearapp.utils.getResponsiveCardPadding
import com.example.prophysiowearapp.utils.getResponsiveIconSize
import com.example.prophysiowearapp.utils.getResponsivePadding

@Composable
fun ResponsiveMenuCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    backgroundColor: Color,
    screenSize: WearScreenSize,
    onClick: () -> Unit
) {
    val iconSize = getResponsiveIconSize(screenSize)
    val cardPadding = getResponsiveCardPadding(screenSize)
    val generalPadding = getResponsivePadding(screenSize)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = generalPadding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(cardPadding)
        ) {
            // Icono
            Box(
                modifier = Modifier
                    .size(iconSize)
                    .clip(CircleShape)
                    .background(color = backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (backgroundColor == MaterialTheme.colors.surface)
                        MaterialTheme.colors.onSurface else Color.White,
                    modifier = Modifier.size(iconSize * 0.6f)
                )
            }

            // Contenido
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = cardPadding)
            ) {
                Text(
                    text = title,
                    style = when (screenSize.size) {
                        com.example.prophysiowearapp.utils.ScreenSize.SMALL -> MaterialTheme.typography.caption1
                        com.example.prophysiowearapp.utils.ScreenSize.MEDIUM -> MaterialTheme.typography.body2
                        com.example.prophysiowearapp.utils.ScreenSize.LARGE -> MaterialTheme.typography.body1
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.caption2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    maxLines = if (screenSize.size == com.example.prophysiowearapp.utils.ScreenSize.SMALL) 1 else 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Flecha
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.size(iconSize * 0.5f)
            )
        }
    }
}
