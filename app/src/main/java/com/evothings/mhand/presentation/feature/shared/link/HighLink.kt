package com.evothings.mhand.presentation.feature.shared.link

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme

@Composable
fun HighLink(
    text: String,
    enableIcon: Boolean = true,
    onClick: () -> Unit
) {
    HighLink(
        text = AnnotatedString(text),
        enableIcon = enableIcon,
        onClick = onClick
    )
}

@Composable
fun HighLink(
    text: AnnotatedString,
    enableIcon: Boolean = true,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            if (enableIcon) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevron_right),
                    tint = MaterialTheme.colorScheme.secondary.copy(0.4f),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondary.copy(0.05f)
        )
    }
}

@Preview
@Composable
private fun HighLinkPreview() {
    MegahandTheme {
        Surface {
            HighLink(
                text = "Test",
                onClick = {}
            )
        }
    }
}