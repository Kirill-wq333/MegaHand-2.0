package com.evothings.mhand.presentation.feature.onboarding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers


@Composable
fun Onboarding(
    modifiers: Modifier = Modifier,
    icon: Int,
    heading: String,
    underHeading: String,
    pageNumber: String,
    onFinish: Boolean,
    visibleButtonTurnBack: Boolean = false
) {
    val modifier =
        if (visibleButtonTurnBack)
            modifiers
                .width(167.dp)
                .height(44.dp)
        else modifiers
            .fillMaxWidth()

    Box(
        modifier = Modifier
            .background(color = colorScheme.onSecondary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.extraGiant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = colorScheme.secondary.copy(.1f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(icon),
                        contentDescription = null,
                        tint = colorScheme.secondary.copy(.4f),
                        modifier = Modifier
                            .padding(MaterialTheme.paddings.medium)
                    )
                }
                Spacer(modifier = Modifier.width(MaterialTheme.spacers.large))
                HeadingAndUnderHeading(
                    heading = heading,
                    underHeading = underHeading,
                    pageNumber = pageNumber
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (visibleButtonTurnBack) {
                    Button(
                        modifier = Modifier
                            .width(167.dp)
                            .height(44.dp),
                        text = stringResource(R.string.turn_back),
                        textColor = colorScheme.secondary,
                        borderColor = colorScheme.secondary.copy(.1f),
                        onClick = {}
                    )
                }
                Button(
                    modifier = modifier,
                    text = stringResource(if (onFinish) R.string.finish_onboarding else R.string.next),
                    textColor = colorScheme.secondary,
                    backgroundColor = ColorTokens.Sunflower,
                    onClick = {}
                )
            }
        }
    }

}

@Composable
fun HeadingAndUnderHeading(
    modifier: Modifier = Modifier,
    heading: String,
    underHeading: String,
    pageNumber: String
) {


    Column(
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = heading,
                color = colorScheme.secondary,
                style = MegahandTypography.headlineMedium,
            )
            Text(
                text = pageNumber,
                color = colorScheme.secondary.copy(.4f),
                style = MegahandTypography.bodyLarge,
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        Text(
            text = underHeading,
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun OnboardingPreview() {
    MegahandTheme(false) {
        Onboarding(
            heading = "Heading",
            underHeading = "Under heading",
            pageNumber = "1/4",
            icon = R.drawable.ic_home,
            onFinish = false,
            visibleButtonTurnBack = true
        )
    }
}