package com.evothings.mhand.presentation.feature.shared.header.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun PrizeAndMoney(
    prize: ImageVector,
    money: String,
    selected: Boolean
) {
    if (selected) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = colorScheme.onBackground.copy(0.05f),
                    shape = MegahandShapes.small
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(MaterialTheme.paddings.large)
            ) {
                Icon(
                    imageVector = prize,
                    contentDescription = "prize",
                    tint = colorScheme.inverseSurface
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
                Text(
                    text = "$money₽",
                    color = colorScheme.onBackground,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
                    fontWeight = FontWeight.W500
                )
            }
        }
    }

}