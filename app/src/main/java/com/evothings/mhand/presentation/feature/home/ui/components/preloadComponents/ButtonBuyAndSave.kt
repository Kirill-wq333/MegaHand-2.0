package com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers


@Composable
fun Buttons(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
               horizontal = MaterialTheme.paddings.extraLarge
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        ButtonBuy(text = "Купить")
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        ButtonSave()
    }
}

@Composable
private fun ButtonBuy(
    text: String
){
    Box(
        modifier = Modifier
            .width(110.dp)
            .height(45.dp)
            .background(color = Color(0xFFFFE600), shape = shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colorScheme.secondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500)))
        )
    }
}

@Composable
private fun ButtonSave() {
    Box(
        modifier = Modifier
            .size(42.dp)
            .border(
                width = 1.dp,
                color = colorScheme.secondary.copy(0.1f),
                shape = shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_favourite),
            contentDescription = "favorite",
            tint = colorScheme.secondary,
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Preview
@Composable
fun PreviewButtons(){
    Buttons()
}