package com.evothings.mhand.presentation.feature.shared.header.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Logo(
    onLongClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .width(50.dp)
            .height(42.dp)
            .background(
                color = Color(0xFF46423E),
                shape = MegahandShapes.small
            )
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {},
                onLongClick = onLongClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.logo),
            contentDescription = "logo",
            tint = colorScheme.primary,
            modifier = Modifier
                .width(38.dp)
        )
    }

}

@Preview
@Composable
fun PreviewLogo() {
    MegahandTheme {
        Logo(
            onLongClick = {}
        )
    }
}