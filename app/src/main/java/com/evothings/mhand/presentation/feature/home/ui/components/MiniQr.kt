package com.evothings.mhand.presentation.feature.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.values.MegahandShapes


@Composable
fun QrCode() {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .background(
                color = colorScheme.inverseSurface.copy(0.1f),
                shape = RoundedCornerShape(
                    topEnd = 9.dp,
                    bottomEnd = 9.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = R.drawable.demo_qr_code,
            contentDescription = "QrCode",
            modifier = Modifier
                .clip(shape = MegahandShapes.large)
                .padding(MaterialTheme.paddings.extraGiant)
                .border(
                    width = 1.dp,
                    color = colorScheme.inverseSurface,
                    shape = MegahandShapes.large
                ),
        )
        Box(
            modifier = Modifier
                .background(color = colorScheme.secondary)
                .size(27.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.logo),
                contentDescription = "logo",
                tint = colorScheme.onSecondary,
                modifier = Modifier
                    .width(24.dp)
                    .height(14.dp)
                    .align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .size(180.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier = Modifier
                    .background(color = colorScheme.secondary, shape = CircleShape)
                    .border(width = 1.dp, color = colorScheme.inverseSurface, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_magnifying_glass),
                    contentDescription = "",
                    tint = colorScheme.onSecondary,
                    modifier = Modifier
                        .padding(MaterialTheme.paddings.medium)
                )
            }
        }

    }
}