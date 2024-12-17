package com.evothings.widget.layout

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
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
fun VerticalRectangle(
    state: WidgetState,
    cardBalance: Int,
    qrBitmap: Bitmap?,
    onReload: () -> Unit
) {
    when(state) {
        is WidgetState.Success -> {
            Content(
                cardBalance = cardBalance,
                qrBitmap = qrBitmap,
                onReload = onReload
            )
        }
        is WidgetState.LoyalityNotAvailable -> {
            LoyalityNotAvailable(
                modifier = GlanceModifier
                    .widgetLayout()
                    .padding(8.dp),
                vertical = true,
                iconSize = 56.dp,
                textSize = 18.sp,
                gap = 16.dp
            )
        }
        is WidgetState.NotAuthorized -> {
            AuthorizationRequired(
                modifier = GlanceModifier
                    .widgetLayout()
                    .padding(8.dp),
                vertical = true,
                titleSize = 18.sp,
                subtitleSize = 12.sp,
                iconSize = 64.dp,
                textGap = 12.dp,
                elementsGap = 16.dp
            )
        }
    }
}

@Composable
private fun Content(
    cardBalance: Int,
    qrBitmap: Bitmap?,
    onReload: () -> Unit
) {
    Column(
        modifier = GlanceModifier
            .widgetLayout()
            .padding(10.dp),
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
        verticalAlignment = Alignment.Vertical.CenterVertically
    ) {
        QrImage(
            modifier = GlanceModifier.defaultWeight(),
            contentScale = ContentScale.Fit,
            bitmap = qrBitmap
        )
        Spacer(
            modifier = GlanceModifier.height(8.dp)
        )
        Balance(
            balance = cardBalance,
            textSize = 18.sp,
            giftIconSize = 20.dp
        )
        Spacer(
            modifier = GlanceModifier.height(6.dp)
        )
        ReloadIconButton(
            modifier = GlanceModifier.size(20.dp),
            onClick = onReload
        )
    }
}