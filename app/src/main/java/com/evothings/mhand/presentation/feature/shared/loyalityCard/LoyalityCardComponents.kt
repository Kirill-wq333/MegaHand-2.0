package com.evothings.mhand.presentation.feature.shared.loyalityCard


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes
import com.evothings.mhand.presentation.theme.values.paddings.LocalPaddings


@Composable
fun BalanceAndCashback(
    money: String,
    button: Boolean,
    visible: Boolean
) {
    var cashback by remember { mutableIntStateOf(3) }
    var visibleButton by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .border(
                width = 1.dp,
                color = colorScheme.primary.copy(.05f),
                shape = RoundedCornerShape(topStart = 9.dp, bottomStart = 9.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .padding(MaterialTheme.paddings.giant)
        ) {
            Items(
                icon = ImageVector.vectorResource(R.drawable.ic_prize),
                text = "$money₽",
                contentDescription = "prize",
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily(listOf(Font(R.font.golos_500)))
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Items(
                icon = ImageVector.vectorResource(R.drawable.ic_back),
                text = "$cashback%",
                contentDescription = "back",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400)))
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
            TextCashBack()
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
            if (visible) {
                if (visibleButton) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = colorScheme.secondary.copy(.05f),
                                shape = MegahandShapes.small
                            )
                            .clickable {
                                visibleButton = button
                                cashback = if (cashback == 0) 3 else 5
                            }
                    ) {
                        Text(
                            text = stringResource(R.string.increase),
                            color = colorScheme.secondary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                            modifier = Modifier
                                .padding(
                                    vertical = MaterialTheme.paddings.medium,
                                    horizontal = MaterialTheme.paddings.large
                                )
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun Items(
    icon: ImageVector,
    text: String,
    contentDescription: String?,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    fontFamily: FontFamily
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = colorScheme.inverseSurface
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        Text(
            text = text,
            color = colorScheme.secondary,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontFamily = fontFamily
        )
    }
}

@Composable
fun TextCashBack(){
    Text(
        text = stringResource(R.string.maximum_cashback_reached),
        color = colorScheme.secondary.copy(0.4f),
        fontSize = 12.sp,
        lineHeight = 15.sp,
        fontWeight = FontWeight.W400,
        fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
    )
}

