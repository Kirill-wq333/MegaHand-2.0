package com.evothings.mhand.presentation.feature.shops.ui.components.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun TopBarSheet(
    modifier: Modifier = Modifier,
    email: String,
    phone: String
) {
    Row(
        modifier = modifier
//            .padding(MaterialTheme.paddings.extraLarge)
    ) {
        Info(
            textHeading = email,
            textUnderHeading = "Free delivery on all orders"
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
        Info(
            textHeading = phone,
            textUnderHeading = "Visa, MasterCard"
        )
    }
}

@Composable
fun Info(
    textHeading: String,
    textUnderHeading: String
) {

    Box(
        modifier = Modifier
            .background(
                color = colorScheme.onSecondary,
                shape = MegahandShapes.medium
                )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.paddings.extraLarge,
                    vertical = MaterialTheme.paddings.large
                ),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = textHeading,
                style = MegahandTypography.bodyLarge,
                color = colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.tiny))
            Text(
                text = textUnderHeading,
                style = MegahandTypography.bodyMedium,
                color = colorScheme.secondary.copy(.4f)
            )
        }
    }
}




@Preview
@Composable
private fun PreviewTopBarSheet() {
    MegahandTheme(false) {
        TopBarSheet(
            email = "some@gamil.com",
            phone = "+7 (999) 000-44-67"
        )
    }
}