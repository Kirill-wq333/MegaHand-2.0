@file:OptIn(ExperimentalFoundationApi::class)
package com.evothings.mhand.presentation.feature.shared.header.ui.notifications.components

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.domain.feature.notification.model.Notification
import com.evothings.domain.feature.notification.model.NotificationType
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.SmallButton
import com.evothings.mhand.presentation.feature.shared.header.ui.sheet.NotificationDragState
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import kotlin.math.roundToInt



@Composable
fun Notification(
    model: Notification,
    onSwipe: (Int) -> Unit,
    onClickUpdate: () -> Unit
) {
    val screenDensity = LocalDensity.current

    val dragState = remember {
        AnchoredDraggableState(
            initialValue = NotificationDragState.START,
            anchors = DraggableAnchors {
                NotificationDragState.START at 0f
                NotificationDragState.END at -(400f * screenDensity.density)
            },
            positionalThreshold = { position: Float -> position * 0.6f },
            velocityThreshold = { with(screenDensity) { 150.dp.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = exponentialDecay()
        )
    }

    val offset by remember {
        derivedStateOf {
            val safeDragOffset =
                if (!dragState.offset.isNaN()) dragState.offset else 0f

            IntOffset(
                x = safeDragOffset.roundToInt(),
                y = 0
            )
        }
    }

    SideEffect {
        if (offset.x <= -300f) {
            onSwipe(model.id)
        }
    }

    NotificationItem(
        type = model.type,
        heading = model.title,
        underTheHeading = model.description,
        gettingDate = model.arrivalTime,
        draggableState = dragState,
        dragOffset = offset,
        onClickUpdate = onClickUpdate
    )
}


@Composable
fun NotificationItem(
    type: NotificationType,
    heading: String,
    underTheHeading: String,
    gettingDate: String,
    draggableState: AnchoredDraggableState<NotificationDragState>,
    dragOffset: IntOffset,
    onClickUpdate: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .anchoredDraggable(
                state = draggableState,
                orientation = Orientation.Horizontal
            )
            .offset { dragOffset },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.extraLarge),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            NotificationIcon(
                modifier = Modifier.weight(0.25f),
                type = type
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
            Column(
                modifier = Modifier
                    .width(230.dp)
            ) {
                TextItem(
                    text = heading,
                    color = colorScheme.secondary,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.tiny))
                TextItem(
                    text = underTheHeading,
                    color = colorScheme.secondary.copy(0.4f),
                    fontSize = 12.sp
                )
                if (type != NotificationType.NEW_VERSION) {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
                    SmallButton(
                        text = stringResource(R.string.update_available_proceed_button),
                        textColor = colorScheme.secondary,
                        backgroundColor = colorScheme.primary,
                        onClick = onClickUpdate
                    )
                }
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
            TextItem(
                text = gettingDate,
                color = colorScheme.secondary.copy(0.4f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun TextItem(
    text: String,
    color: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.W400,
    fontFamily: FontFamily = FontFamily(listOf(Font(R.font.golos_400)))
){
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        lineHeight = 20.sp,
        fontWeight = fontWeight,
        fontFamily = fontFamily
    )
}

@Composable
private fun NotificationIcon(
    modifier: Modifier,
    type: NotificationType
) {

    val iconResId = remember {
        when(type) {
            NotificationType.NEW_VERSION -> R.drawable.ic_sparkles
            NotificationType.ALERT -> R.drawable.ic_attention
            NotificationType.INFOFMATION -> R.drawable.ic_news
        }
    }

    val iconTint =
        when(type) {
            NotificationType.ALERT -> colorScheme.error
            NotificationType.INFOFMATION -> colorScheme.secondary
            NotificationType.NEW_VERSION -> ColorTokens.Graphite
        }

    val backgroundColor =
        when(type) {
            NotificationType.ALERT -> colorScheme.error.copy(alpha = 0.1f)
            NotificationType.INFOFMATION -> colorScheme.secondary.copy(alpha = 0.1f)
            NotificationType.NEW_VERSION -> ColorTokens.Sunflower
        }

    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconResId),
            tint = iconTint,
            contentDescription = null,
            modifier = modifier.padding(6.dp)
        )
    }

}