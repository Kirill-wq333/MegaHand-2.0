package com.evothings.mhand.presentation.feature.shared.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens

@Preview
@Composable
private fun ButtonsPreview() {
    MegahandTheme {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    text = "Some test",
                    textColor = ColorTokens.Graphite,
                    backgroundColor = ColorTokens.Sunflower,
                    onClick = {}
                )
                SmallButton(
                    text = "Some test",
                    textColor = ColorTokens.Graphite,
                    borderColor = ColorTokens.Sunflower,
                    onClick = {}
                )
            }
        }
    }
}

//@Composable
//fun Button(
//    modifier: Modifier = Modifier,
//    backgroundColor: Color = Color.Unspecified,
//    shape: Shape = MaterialTheme.shapes.medium,
//    borderColor: Color = Color.Unspecified,
//    isEnabled: Boolean = true,
//    onClick: () -> Unit,
//    content: @Composable () -> Unit
//) {
//    MButton(
//        modifier = modifier,
//        backgroundColor = backgroundColor,
//        shape = shape,
//        borderColor = borderColor,
//        isEnabled = isEnabled,
//        onClick = onClick,
//        content = content
//    )
//}

@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Unspecified,
    borderColor: Color = Color.Unspecified,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    MButton(
        modifier = modifier,
        backgroundColor = backgroundColor,
        shape = MaterialTheme.shapes.medium,
        borderColor = borderColor,
        isEnabled = isEnabled,
        onClick = onClick
    ) {
        ButtonText(
            text = text,
            color = textColor,
            padding = PaddingValues(
                vertical = 12.dp,
                horizontal = 16.dp
            ),
        )
    }
}

@Composable
fun SmallButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Unspecified,
    borderColor: Color = Color.Unspecified,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    MButton(
        modifier = modifier,
        backgroundColor = backgroundColor,
        shape = MaterialTheme.shapes.small,
        borderColor = borderColor,
        isEnabled = isEnabled,
        onClick = onClick
    ) {
        ButtonText(
            text = text,
            color = textColor,
            padding = PaddingValues(
                vertical = 6.dp,
                horizontal = 9.dp
            )
        )
    }
}

@Composable
private fun MButton(
    modifier: Modifier,
    backgroundColor: Color,
    shape: Shape,
    borderColor: Color,
    isEnabled: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .alpha(
                alpha = if (isEnabled) 1.0f else 0.3f
            )
            .background(
                color = backgroundColor,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { if (isEnabled) onClick() }
            ),
        contentAlignment = Alignment.Center
    ) {
        content.invoke()
    }
}

@Composable
private fun ButtonText(
    text: String,
    color: Color,
    padding: PaddingValues
) {
    Text(
        text = text,
        color = color,
        style = typography.labelLarge,
        modifier = Modifier
            .padding(padding)
    )
}