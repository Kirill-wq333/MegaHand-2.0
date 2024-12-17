package com.evothings.mhand.presentation.feature.navigation.bottomBar.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings


@Composable
fun BottomBarNavigation() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = MaterialTheme.paddings.giant),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            BottomItem(
                selected = true,
                onClick = {},
                imageVector = ImageVector.vectorResource(R.drawable.ic_home),
                contentDescription = "home"
            )
            BottomItem(
                selected = true,
                onClick = {},
                imageVector = ImageVector.vectorResource(R.drawable.ic_catalog),
                contentDescription = "catalog"
            )
            BottomItem(
                selected = true,
                onClick = {},
                imageVector = ImageVector.vectorResource(R.drawable.ic_card),
                contentDescription = "card"
            )
            BottomItem(
                selected = true,
                onClick = {},
                imageVector = ImageVector.vectorResource(R.drawable.ic_shop),
                contentDescription = "shop"
            )
            BottomItem(
                selected = true,
                onClick = {},
                imageVector = ImageVector.vectorResource(R.drawable.ic_account),
                contentDescription = "account"
            )
            BottomItem(
                selected = true,
                onClick = {},
                imageVector = ImageVector.vectorResource(R.drawable.ic_other),
                contentDescription = "other"
            )
        }
    }
}

@Composable
private fun BottomItem(
    selected: Boolean,
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String?
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = if (selected) Color(0xFFFFE600) else Color.White,
                shape = shapes.extraSmall
            )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier
                .padding(MaterialTheme.paddings.large)
        )
    }
}

@Preview
@Composable
fun PreviewBottomBarNavigation(){
    val navController = rememberNavController()

    BottomBarNavigation()
}