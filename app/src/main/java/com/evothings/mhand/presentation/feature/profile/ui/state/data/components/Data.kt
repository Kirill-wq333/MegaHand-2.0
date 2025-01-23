package com.evothings.mhand.presentation.feature.profile.ui.state.data.components

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.profile.ui.state.requiredFields.TextAndTextField
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.text.LabelTextField
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun Data(
    modifier: Modifier = Modifier,
    nameAndSurName: String,
    birthday: String,
    city: String,
    phoneNumber: String,
    email: String,
    onEditProfile: () -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = nameAndSurName,
                color = colorScheme.secondary,
                style = MegahandTypography.titleLarge
            )
            IconButton(
                icon = ImageVector.vectorResource(R.drawable.ic_edit),
                tint = colorScheme.secondary,
                onClick = onEditProfile
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Row {
            InfoTextArea(
                label = stringResource(id = R.string.date_of_birth),
                value = birthday,
                modifier = Modifier.weight(0.5f)
            )
            Spacer(
                modifier = Modifier
                    .width(MaterialTheme.spacers.medium)
            )
            InfoTextArea(
                label = stringResource(id = R.string.city),
                value = city,
                modifier = Modifier.weight(0.5f)
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Row {
            InfoTextArea(
                label = stringResource(id = R.string.phone_number),
                value = phoneNumber,
                enableGift = false,
                modifier = Modifier.weight(0.5f)
            )
            Spacer(
                modifier = Modifier
                    .width(MaterialTheme.spacers.medium)
            )
            InfoTextArea(
                label = stringResource(id = R.string.profile_email),
                value = email,
                modifier = Modifier.weight(0.5f)
            )
        }
    }

}


@Composable
private fun InfoTextArea(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    enableGift: Boolean = true
) {
    Column(modifier = modifier) {

        GiftIconLabel(
            label = label,
            enableGift = enableGift,
            isIconActive = value.isNotEmpty()
        )

        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.normal)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorScheme.secondary.copy(0.1f),
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = typography.bodyLarge,
                color = colorScheme.secondary,
                modifier = Modifier
                    .padding(MaterialTheme.spacers.medium)
                    .fillMaxWidth()
                    .basicMarquee()
            )
        }
    }
}

@Composable
fun GiftIconLabel(
    label: String,
    enableGift: Boolean = true,
    isIconActive: Boolean
) {
    val giftTint =
        if (isIconActive)
            colorScheme.inverseSurface
        else
            colorScheme.secondary.copy(0.6f)

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            style = typography.bodyLarge,
            color = colorScheme.secondary.copy(0.6f),
            modifier = Modifier.padding(horizontal = 6.dp)
        )
        if (enableGift) {
            Spacer(
                modifier = Modifier
                    .width(MaterialTheme.spacers.tiny)
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_prize),
                tint = giftTint,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}