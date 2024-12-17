package com.evothings.mhand.presentation.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R

@Composable
fun LoadingScreen(
    long: String,
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFF46423E)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.logo),
                contentDescription = "Logo",
                tint = Color(0xFFFFE600),
            )
            Spacer(modifier = Modifier.height(192.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(28.dp),
                color = Color(0xFFFFE600),
                strokeWidth = 2.dp,
                trackColor = colorScheme.secondary.copy(0.6f)
            )
            Spacer(modifier = Modifier.height(90.dp))
            Text(
                text = long,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400)))
            )
        }
    }
}

@Composable
fun LoadingTechnicalServiceScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFF46423E)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_setting),
                contentDescription = "Logo",
                tint = Color(0xFFFFE600),
                modifier = Modifier
                    .size(128.dp)
            )
            Spacer(modifier = Modifier.height(192.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(28.dp),
                color = Color(0xFFFFE600),
                strokeWidth = 2.dp,
                trackColor = colorScheme.secondary.copy(0.6f)
            )
            Spacer(modifier = Modifier.height(90.dp))
            Text(
                text = stringResource(R.string.server_tech_works),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily(listOf(Font(R.font.golos_500)))
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.app_unavailable),
                fontSize = 16.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                modifier = Modifier
                    .padding(horizontal = 70.dp)
            )
        }
    }
}


@Preview
@Composable
fun PreviewLaunch(){
    LoadingScreen(
        long = ""
    )
}


@Preview
@Composable
fun PreviewLongLaunch(){
    LoadingScreen(
        long = "Еще немного…"
    )
}

@Preview
@Composable
fun PreviewTechnicalService() {
    LoadingTechnicalServiceScreen()
}