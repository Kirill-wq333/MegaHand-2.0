package com.evothings.mhand.presentation.feature.checkout.ui.components.delivery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R

@Composable
fun CDEKViewBox(
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .size(350.dp)
            .clickable(
                onClick = onClick
            )
            .clip(RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.Center

    ){
        Image(
            modifier = Modifier
                .fillMaxSize()
                .blur(10.dp)
                .background(
                    shape = RoundedCornerShape(3.dp),
                    color = Color.Transparent
                ),
            painter = painterResource(R.drawable.cdek_map_preview),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .blur(1.dp, radiusY = 3.dp)
                .border(
                    width = 50.dp,
                    color = Color.Black.copy(0.3f),
                    shape = RoundedCornerShape(35.dp)
                )
                .padding(horizontal = 9.dp),
            text = "Нажмите для отображения карты",
            style = typography.titleMedium,
            color = Color.Transparent
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "Нажмите для отображения карты",
            color = Color.White,
            style = typography.titleMedium,
        )
    }
}