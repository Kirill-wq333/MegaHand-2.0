package com.evothings.mhand.presentation.feature.snackbar.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun SnackCoupon(
    messageHeading: String,
    underTheMessageHeading: String,
    backgroundColor: Color = colorScheme.secondary
){
    val barWidthFraction = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        barWidthFraction.animateTo(
            targetValue = 0.0f,
            animationSpec = tween(
                durationMillis = 4000,
                easing = LinearEasing
            )
        )
    }

    val textColor =
        if (backgroundColor == colorScheme.secondary) colorScheme.onSecondary else Color.White

    Snackbar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = backgroundColor,
        contentColor = Color.White,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.paddings.extraLarge),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = messageHeading,
                    color = textColor,
                    style = MegahandTypography.labelLarge
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.tiny))
                Text(
                    text = underTheMessageHeading,
                    color = textColor,
                    style = MegahandTypography.bodyMedium
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(barWidthFraction.value)
                    .height(2.dp)
                    .background(
                        color = colorScheme.primary,
                        shape = MaterialTheme.shapes.large
                    ),
            )
        }
    }
}

@Preview
@Composable
fun PreviewConditionCoupon() {
    MegahandTheme(true) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            SnackCoupon(
                messageHeading = "Купон успешно отправлен!",
                underTheMessageHeading = "Ожидай СМС-сообщение",
            )
            SnackCoupon(
                messageHeading = "Ты ввел неверный код 3 раза подряд",
                underTheMessageHeading = "Вход был временно заблокирован",
            )
        }
    }
}