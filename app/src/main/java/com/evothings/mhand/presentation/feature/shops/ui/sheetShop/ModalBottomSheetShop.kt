package com.evothings.mhand.presentation.feature.shops.ui.sheetShop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun ModalBottomSheetShop(
    street: String,
    workingDays: String,
    discountOnThisDay: String,
    selected: Boolean
) {
    val colorBorder =
        if (selected) colorScheme.secondary.copy(.05f)
        else colorScheme.onSecondary.copy(.05f)

    Box(
        modifier = Modifier
            .background(color = colorScheme.onSecondary)
    ) {

        Column(
            modifier = Modifier
                .padding(MaterialTheme.paddings.extraGiant)
        ) {
            Text(
                text = street,
                style = MegahandTypography.headlineMedium,
                color = colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Text(
                text = workingDays,
                style = MegahandTypography.bodyLarge,
                color = colorScheme.secondary
            )
            Text(
                text = discountOnThisDay,
                style = MegahandTypography.bodyMedium,
                color = colorScheme.secondary.copy(.4f)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier
                        .width(198.dp)
                        .height(44.dp),
                    text = stringResource(R.string.discount_calendar),
                    textColor = colorScheme.secondary,
                    backgroundColor = colorScheme.primary,
                    onClick = {}
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
                Box(
                    modifier = Modifier
                        .background(
                            color = colorBorder,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_phone),
                        contentDescription = null,
                        tint = colorScheme.secondary,
                        modifier = Modifier
                            .padding(MaterialTheme.paddings.large)
                    )
                }
            }
        }

    }

}


@Preview
@Composable
private fun PreviewModalBottomSheetShop() {
    MegahandTheme(false) {
        ModalBottomSheetShop(
            street = "Street",
            workingDays = "9:00 - 21:00",
            discountOnThisDay = "Discount on this day",
            selected = true,
        )
    }
}