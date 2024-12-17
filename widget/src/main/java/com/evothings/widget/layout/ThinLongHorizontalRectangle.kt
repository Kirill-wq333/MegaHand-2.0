package com.evothings.widget.layout

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import com.evothings.widget.components.Balance
import com.evothings.widget.components.QrImage
import com.evothings.widget.components.ReloadIconButton
import com.evothings.widget.components.modifier.widgetLayout
import com.evothings.widget.components.state.AuthorizationRequired
import com.evothings.widget.components.state.LoyalityNotAvailable
import com.evothings.widget.viewmodel.WidgetState

@Composable
fun ThinLongHorizontalRectangle(
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
                    .padding(6.dp),
                vertical = false,
                iconSize = 48.dp,
                textSize = 16.sp,
                gap = 12.dp
            )
        }
        WidgetState.NotAuthorized -> {
            AuthorizationRequired(
                modifier = GlanceModifier
                    .widgetLayout()
                    .padding(6.dp),
                vertical = false,
                titleSize = 16.sp,
                subtitleSize = 13.sp,
                iconSize = 48.dp,
                textGap = 9.dp,
                elementsGap = 12.dp
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
            .padding(5.dp),
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
        verticalAlignment = Alignment.Vertical.CenterVertically
    ) {
        Balance(
            modifier = GlanceModifier.padding(start = 12.dp),
            balance = balance,
            textSize = 18.sp,
            giftIconSize = 24.dp
        )
        Spacer(
            modifier = GlanceModifier.width(16.dp)
        )
        ReloadIconButton(
            modifier = GlanceModifier.size(22.dp),
            onClick = onReload
        )
        Spacer(
            modifier = GlanceModifier.defaultWeight()
        )
        QrImage(
            modifier = GlanceModifier.defaultWeight(),
            contentScale = ContentScale.Fit,
            bitmap = qrBitmap
        )
    }
}