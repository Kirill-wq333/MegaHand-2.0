package com.evothings.mhand.presentation.feature.shops.ui.components.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun ContactInfo(
    modifier: Modifier,
    email: String,
    phone: String
) {
    Row(modifier) {
        ContactInfoItem(
            modifier = Modifier.weight(0.5f),
            title = email,
            subtitle = stringResource(R.string.shop_contact_mail_text)
        )
        Spacer(
            modifier = Modifier
                .width(MaterialTheme.spacers.medium)
        )
        ContactInfoItem(
            modifier = Modifier.weight(0.5f),
            title = phone,
            subtitle = stringResource(R.string.shop_contact_phone_text)
        )
    }
}

@Composable
private fun ContactInfoItem(
    modifier: Modifier,
    title: String,
    subtitle: String
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = MaterialTheme.shapes.medium
            )
            .padding(
                vertical = MaterialTheme.spacers.normal,
                horizontal = MaterialTheme.spacers.medium
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.tiny)
        )
        Text(
            text = subtitle,
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary.copy(0.4f)
        )
    }
}