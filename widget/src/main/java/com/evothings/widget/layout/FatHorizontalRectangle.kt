package com.evothings.widget.layout

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.padding
import androidx.glance.layout.size
import com.evothings.widget.components.Balance
import com.evothings.widget.components.QrImage
import com.evothings.widget.components.ReloadIconButton
import com.evothings.widget.components.modifier.widgetLayout
import com.evothings.widget.components.state.AuthorizationRequired
import com.evothings.widget.components.state.LoyalityNotAvailable
import com.evothings.widget.viewmodel.WidgetState

@Composable
fun FatHorizontalRectangle(
    state: WidgetState,
    cardBalance: Int,
    qrBitmap: Bitmap?,
    onReload: () -> Unit
) {
    when(state) {
        WidgetState.Success -> {
            Content(
                balance = cardBalance,
                qrBitmap = qrBitmap,
                onReload = onReload
            )
        }
        WidgetState.LoyalityNotAvailable -> {
            LoyalityNotAvailable(
                modifier = GlanceModifier
                    .widgetLayout()
                    .padding(8.dp),
                vertical = false,
                iconSize = 32.dp,
                textSize = 12.sp,
                gap = 9.dp
            )
        }
        WidgetState.NotAuthorized -> {
            AuthorizationRequired(
                modifier = GlanceModifier
                    .widgetLayout()
                    .padding(8.dp),
                vertical = false,
                titleSize = 12.sp,
                subtitleSize = 10.sp,
                iconSize = 32.dp,
                textGap = 4.dp,
                elementsGap = 9.dp
            )
        }
    }
}

@Composable
private fun Content(
    balance: Int,
    qrBitmap: Bitmap?,
    onReload: () -> Unit
) {
    Row(
        modifier = GlanceModifier
            .widgetLayout()
            .padding(6.dp),
        verticalAlignment = Alignment.Vertical.Top
    ) {
        Column(
            modifier = GlanceModifier.fillMaxHeight()
        ) {
            Balance(
                balance = balance,
                giftIconSize = 20.dp,
                textSize = 16.sp,
            )
            Box(
                modifier = GlanceModifier.defaultWeight(),
                contentAlignment = Alignment.BottomStart
            ) {
                ReloadIconButton(
                    modifier = GlanceModifier.size(16.dp),
                    onClick = onReload
                )
            }
        }
        QrImage(
            modifier = GlanceModifier
                .defaultWeight()
                .padding(start = 14.dp),
            bitmap = qrBitmap
        )
    }
}