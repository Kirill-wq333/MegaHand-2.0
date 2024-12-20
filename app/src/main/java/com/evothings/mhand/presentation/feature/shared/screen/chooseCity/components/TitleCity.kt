package com.evothings.mhand.presentation.feature.shared.screen.chooseCity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun TitleCity(
    title: String,
    nameCity: String
){

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            color = colorScheme.secondary,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500)))
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = nameCity,
                color = colorScheme.secondary,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                modifier = Modifier
                    .padding(MaterialTheme.paddings.extraLarge)
            )
            HorizontalDivider(modifier = Modifier.fillMaxWidth().background(color = colorScheme.secondary.copy(0.05f)))
        }

    }
}

