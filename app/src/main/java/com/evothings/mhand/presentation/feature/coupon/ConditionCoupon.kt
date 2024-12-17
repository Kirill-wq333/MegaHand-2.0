package com.evothings.mhand.presentation.feature.coupon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun ConditionCoupon(
    heading: String,
    underTheHeading: String,
    color: Color
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.paddings.extraLarge)
            .background(
                color = color,
                shape = MegahandShapes.medium
            ),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .padding(MaterialTheme.paddings.extraLarge),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = heading,
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
                fontWeight = FontWeight.W500
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.tiny))
            Text(
                text = underTheHeading,
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                fontWeight = FontWeight.W400
            )
        }
    }
}

@Preview
@Composable
fun PreviewConditionCoupon() {
    MegahandTheme {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            ConditionCoupon(
                heading = "Купон успешно отправлен!",
                underTheHeading = "Ожидай СМС-сообщение",
                color = colorScheme.secondary
            )
            ConditionCoupon(
                heading = "Ты ввел неверный код 3 раза подряд",
                underTheHeading = "Вход был временно заблокирован",
                color = colorScheme.error
            )
        }
    }
}