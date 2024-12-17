package com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun SizeAndStars(
    textSize: String,
    estimation: String
) {
    Row(
        modifier = Modifier
            .padding(start = MaterialTheme.paddings.extraLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconAndTextItem(
            icon = ImageVector.vectorResource(R.drawable.ic_tag),
            text = textSize,
            fontSize = 12.sp,
            fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
            color = colorScheme.secondary.copy(0.6f)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        IconAndTextItem(
            icon = ImageVector.vectorResource(R.drawable.ic_star),
            text = estimation,
            fontSize = 12.sp,
            fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
            color = colorScheme.secondary.copy(0.6f)
        )
    }
}


@Composable
private fun IconAndTextItem(
    fontSize: TextUnit,
    text: String,
    color: Color,
    icon: ImageVector,
    fontFamily: FontFamily,
    fontWeight: FontWeight = FontWeight.W400
) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = "star"
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.tiny))
        Text(
            text = text,
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontFamily = fontFamily
        )
    }
}