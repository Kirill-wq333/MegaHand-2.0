package com.evothings.widget.layout

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.padding
import com.evothings.widget.components.Balance
import com.evothings.widget.components.QrImage
import com.evothings.widget.components.modifier.widgetLayout
import com.evothings.widget.components.state.AuthorizationRequired
import com.evothings.widget.components.state.LoyalityNotAvailable
import com.evothings.widget.viewmodel.WidgetState

@Composable
fun SmallSquare(
    state: WidgetState,
    cardBalance: Int,
    qrBitmap: Bitmap?
) {
    when(state) {
        WidgetState.Success -> {
            Content(
                cardBalance = cardBalance,
                qrBitmap = qrBitmap
            )
        }
        WidgetState.LoyalityNotAvailable -> {
            LoyalityNotAvailable(
                modifier = GlanceModifier
                    .widgetLayout()
                    .padding(2.dp),
                vertical = true,
                iconSize = 12.dp,
                textSize = 7.sp,
                gap = 3.dp
            )
        }
        WidgetState.NotAuthorized -> {
            AuthorizationRequired(
                modifier = GlanceModifier
                    .widgetLayout()
                    .padding(2.dp),
                vertical = true,
                titleSize = 7.sp,
                subtitleSize = 5.sp,
                iconSize = 10.dp,
                textGap = 3.dp,
                elementsGap = 3.dp
            )
        }
    }
}

@Composable
private fun Content(
    cardBalance: Int,
    qrBitmap: Bitmap?
) {
    Column(
        modifier = GlanceModifier
            .widgetLayout()
            .padding(3.dp),
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
        verticalAlignment = Alignment.Vertical.CenterVertically
    ) {
        Balance(
            balance = cardBalance,
            textSize = 12.sp,
            giftIconSize = 12.dp
        )
        QrImage(
            modifier = GlanceModifier.defaultWeight(),
            contentScale = ContentScale.Fit,
            bitmap = qrBitmap
        )
    }
}