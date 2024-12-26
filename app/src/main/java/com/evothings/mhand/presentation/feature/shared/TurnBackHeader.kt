package com.evothings.mhand.presentation.feature.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import androidx.compose.material3.MaterialTheme.typography

@Composable
fun TurnBackHeader(
    modifier: Modifier = Modifier,
    label: String,
    showArrow: Boolean = true,
    onBack: () -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 15.dp,
                horizontal = if (showArrow) 5.dp else 20.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showArrow) {
            IconButton(
                modifier = Modifier.size(28.dp),
                onClick = onBack
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow),
                    contentDescription = null
                )
            }
        }
        Text(
            text = label,
            style = typography.headlineMedium
        )
    }

}