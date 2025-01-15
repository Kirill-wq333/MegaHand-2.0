package com.evothings.mhand.presentation.feature.profile.ui.state.data.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.profile.ui.state.requiredFields.TextAndTextField
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.text.LabelTextField
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun Data(
    modifier: Modifier = Modifier,
    nameAndSurName: String
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
                onClick = {}
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Row {
            TextAndTextField(
                text = stringResource(R.string.date_of_birth),
                modifier = Modifier.weight(.5f),
                textField = "29.01.1800",
                visiblePrize = true,
                colorPrize = colorScheme.inverseSurface
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
            TextAndTextField(
                text = stringResource(R.string.city),
                modifier = Modifier.weight(.5f),
                textField = "Тольятти",
                visiblePrize = true,
                colorPrize = colorScheme.inverseSurface
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Row {
            LabelTextField(
                modifier = Modifier.weight(.5f),
                label = stringResource(R.string.phone_number),
                value = "+7 983 254 65 89",
                onValueChange = {}
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
            TextAndTextField(
                text = stringResource(R.string.profile_email),
                modifier = Modifier.weight(.5f),
                textField = "",
                visiblePrize = true,
                colorPrize = colorScheme.secondary
            )
        }
    }

}