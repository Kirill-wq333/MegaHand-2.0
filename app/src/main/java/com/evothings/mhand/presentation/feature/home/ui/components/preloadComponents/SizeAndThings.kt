package com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun SizeAndStars(
    textSize: String,
    estimation: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconAndTextItem(
            icon = ImageVector.vectorResource(R.drawable.ic_tag),
            text = textSize,
            style = MegahandTypography.bodyMedium,
            color = colorScheme.secondary.copy(0.6f)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        IconAndTextItem(
            icon = ImageVector.vectorResource(R.drawable.ic_star),
            text = estimation,
            style = MegahandTypography.bodyMedium,
            color = colorScheme.secondary.copy(0.6f)
        )
    }
}


@Composable
private fun IconAndTextItem(
    text: String,
    color: Color,
    icon: ImageVector,
    style: TextStyle,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "star",
            tint = colorScheme.secondary.copy(0.4f),
            modifier = Modifier
                .size(15.dp)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.tiny))
        Text(
            text = text,
            color = color,
            style = style
        )
    }
}