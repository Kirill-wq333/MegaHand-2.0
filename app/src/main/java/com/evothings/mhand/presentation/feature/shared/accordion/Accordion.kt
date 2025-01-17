package com.evothings.mhand.presentation.feature.shared.accordion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.evothings.mhand.R
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings

@Composable
fun Accordion(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Accordion(
        modifier = modifier,
        title = title,
        isExpanded = false,
        onExpandChange = {},
        content = content
    )
}

@Composable
fun Accordion(
    modifier: Modifier = Modifier,
    title: String,
    isExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {

    var expanded by remember(isExpanded) {
        mutableStateOf(isExpanded)
    }

    Column(
        modifier = modifier
    ) {
        AccordionLink(
            text = title,
            isExpanded = expanded,
            onExpand = {
                expanded = !expanded
                onExpandChange(expanded)
            }
        )
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            content.invoke()
        }
    }

}

@Composable
private fun AccordionLink(
    text: String,
    isExpanded: Boolean,
    onExpand: () -> Unit
) {

    val iconResId = remember(isExpanded) {
        if (isExpanded)
            R.drawable.ic_chevron_top
        else
            R.drawable.ic_chevron_bottom
    }

    val iconTint = MaterialTheme.colorScheme.secondary.copy(
            alpha = if (isExpanded) 1.0f else 0.4f
        )

    val background =
        if (isExpanded)
            MaterialTheme.colorScheme.secondary.copy(0.15f)
        else
            Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onExpand
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.paddings.extraLarge,
                    horizontal = MaterialTheme.paddings.giant
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Icon(
                imageVector = ImageVector.vectorResource(iconResId),
                tint = iconTint,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview
@Composable
private fun AccordionPreview() {
    MegahandTheme {
        Surface {
            Accordion(
                title = "Test",
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            )
        }
    }
}