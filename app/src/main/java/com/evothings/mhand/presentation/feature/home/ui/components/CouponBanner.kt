package com.evothings.mhand.presentation.feature.home.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings


@Composable
fun CouponBanner(
    banner: Int,
    selected: Boolean
) {
    var visibleCoupon by remember { mutableStateOf(true) }

    if (visibleCoupon) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.paddings.extraLarge
                ),
            contentAlignment = Alignment.TopEnd
        ) {

            AsyncImage(
                model = banner,
                contentDescription = null,
            )
            Box(
                modifier = Modifier
                    .padding(MaterialTheme.paddings.extraLarge),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                    contentDescription = "Close",
                    modifier = Modifier
                        .clickable { visibleCoupon = selected }
                        .padding(MaterialTheme.paddings.medium)
                )

            }
        }

    }
}


@Preview
@Composable
fun PreviewBanners(){
    CouponBanner(
        banner = R.drawable.loyality_onboarding_banner,
        selected = true
    )
}