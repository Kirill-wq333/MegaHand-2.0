package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings

@Composable
fun HintsList(
    text: String,
    onClick: () -> Unit
){
    HintsList(
        text = AnnotatedString(text),
        onClick = onClick
    )
}

@Composable
fun HintsList(
    text: AnnotatedString,
    onClick: () -> Unit
    ) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = text,
                color = colorScheme.secondary,
                style = MegahandTypography.bodyLarge,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.extraLarge)
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_right),
                contentDescription = null,
                tint = colorScheme.secondary,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.extraLarge)
            )
        }
    }

}