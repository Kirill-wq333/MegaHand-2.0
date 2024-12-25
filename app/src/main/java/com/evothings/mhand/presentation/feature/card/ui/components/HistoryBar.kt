package com.evothings.mhand.presentation.feature.card.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun HistoryBar(){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.transactions_history),
            color = colorScheme.secondary,
            style = MegahandTypography.headlineMedium,
            modifier = Modifier
                .padding(vertical = MaterialTheme.paddings.medium)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Этот год",
                color = colorScheme.secondary,
                style = MegahandTypography.labelLarge,
                modifier = Modifier
                    .padding(vertical = MaterialTheme.paddings.medium)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.tiny))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_bottom),
                contentDescription = null,
                tint = colorScheme.secondary,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.paddings.medium)
            )
        }
    }
}