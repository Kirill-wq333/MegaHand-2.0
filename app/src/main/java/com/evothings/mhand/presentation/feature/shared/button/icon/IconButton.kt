package com.evothings.mhand.presentation.feature.shared.button.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens

@Preview
@Composable
private fun IconButtonPreview() {
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
                IconButton(
                    icon = ImageVector.vectorResource(id = R.drawable.ic_notifications),
                    tint = ColorTokens.Graphite,
                    backgroundColor = ColorTokens.Sunflower,
                    badgeValue = 4,
                    onClick = {}
                )
                SmallIconButton(
                    icon = ImageVector.vectorResource(id = R.drawable.ic_plus),
                    tint = ColorTokens.Graphite,
                    borderColor = ColorTokens.Sunflower,
                    badgeValue = 4,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconPadding: Dp = 9.dp,
    tint: Color = Color.Unspecified,
    backgroundColor: Color = Color.Unspecified,
    isEnabled: Boolean = true,
    borderColor: Color = Color.Unspecified,
    badgeValue: Int = 0,
    onClick: () -> Unit
) {
    MIconButton(
        modifier = modifier,
        icon = icon,
        iconPadding = iconPadding,
        iconSize = 24.dp,
        iconTint = tint,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        isEnabled = isEnabled,
        shape = shapes.medium,
        badgeValue = badgeValue,
        onClick = onClick
    )
}

@Composable
fun SmallIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconPadding: Dp = 6.dp,
    tint: Color = Color.Unspecified,
    backgroundColor: Color = Color.Unspecified,
    isEnabled: Boolean = true,
    borderColor: Color = Color.Unspecified,
    badgeValue: Int = 0,
    onClick: () -> Unit
) {
    MIconButton(
        modifier = modifier,
        icon = icon,
        iconPadding = iconPadding,
        iconSize = 20.dp,
        iconTint = tint,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        isEnabled = isEnabled,
        shape = shapes.small,
        badgeValue = badgeValue,
        onClick = onClick
    )
}

@Composable
private fun MIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconPadding: Dp,
    iconSize: Dp,
    iconTint: Color,
    backgroundColor: Color = Color.Unspecified,
    isEnabled: Boolean = true,
    shape: CornerBasedShape = shapes.medium,
    borderColor: Color = Color.Unspecified,
    badgeValue: Int = 0,
    onClick: () -> Unit
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
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { if (isEnabled) onClick() }
            )
            .backgroundBorder(
                color = borderColor,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier
                .padding(iconPadding)
                .size(iconSize)
        )
        if (badgeValue > 0) {
            CounterBadge(
                value = badgeValue
            )
        }
    }
}

@Composable
private fun BoxScope.CounterBadge(value: Int) {
    Box(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .background(
                color = colorScheme.error,
                shape = shapes.small
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$value",
            color = Color.White,
            style = typography.bodySmall,
            modifier = Modifier
                .padding(
                    vertical = 2.dp,
                    horizontal = 4.dp
                )
        )
    }
}