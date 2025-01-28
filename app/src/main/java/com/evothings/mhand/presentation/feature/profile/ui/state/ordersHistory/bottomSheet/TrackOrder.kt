package com.evothings.mhand.presentation.feature.profile.ui.state.ordersHistory.bottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

private data class TrackOrder(
    val number: String,
    val track: String
)

@Preview
@Composable
private fun TrackOrder() {
    MegahandTheme {
        Surface() {
            MhandModalBottomSheet(
                onDismissRequest = {}
            ) {
                TrackOrderList(
                    onCopy = {}
                )
            }
        }
    }
}

@Composable
fun TrackOrderList(
    modifier: Modifier = Modifier,
    onCopy: () -> Unit
) {
    val track = listOf(
        TrackOrder(
            number = "1.",
            track = "125150980634"
        ),
        TrackOrder(
            number = "2.",
            track = "219519592395"
        ),
        TrackOrder(
            number = "3.",
            track = "3285532385"
        )
    )

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .modalBottomSheetPadding()
    ) {
        items(track) { it ->
            TrackOrder(
                number = it.number,
                track = it.track,
                onCopy = onCopy
            )
        }
    }

}

@Composable
fun TrackOrder(
    modifier: Modifier = Modifier,
    number: String,
    track: String,
    onCopy: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.extraLarge),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(
                    text = number,
                    color = colorScheme.secondary.copy(.4f),
                    style = MegahandTypography.bodyLarge
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
                Text(
                    text = track,
                    color = colorScheme.secondary,
                    style = MegahandTypography.bodyLarge
                )
            }
            IconButton(
                icon = ImageVector.vectorResource(R.drawable.ic_copy),
                tint = colorScheme.secondary,
                onClick = onCopy
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            color = colorScheme.secondary.copy(.05f)
        )
    }
}