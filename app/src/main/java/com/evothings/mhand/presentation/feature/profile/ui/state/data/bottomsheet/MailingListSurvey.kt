package com.evothings.mhand.presentation.feature.profile.ui.state.data.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers


@Composable
fun MailingListSurveyBottomSheet(
    modifier: Modifier = Modifier
) {
    MhandModalBottomSheet(
        onDismissRequest = {}
    ) {
        MailingListSurvey()
    }
}

@Composable
fun MailingListSurvey(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.paddings.extraGiant),
    ) {
        Text(
            text = "Согласны ли вы на получение мобильных сообщений и email рассылок?",
            color = colorScheme.secondary,
            style = MegahandTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Action(
            onRefuse = {},
            onAgree = {}
        )
    }
}

@Composable
private fun Action(
    modifier: Modifier = Modifier,
    onRefuse: () -> Unit,
    onAgree: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            modifier = Modifier.weight(.5f),
            text = "Отказываюсь",
            onClick = onRefuse,
            textColor = ColorTokens.Graphite,
            borderColor = colorScheme.secondary.copy(.1f)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
        Button(
            modifier = Modifier.weight(.5f),
            text = "Соглашаюсь",
            onClick = onAgree,
            textColor = ColorTokens.Graphite,
            backgroundColor = ColorTokens.Sunflower
        )
    }
}

@Preview
@Composable
private fun MailingListSurveyPreview() {
    MegahandTheme {
        Surface {
            MailingListSurveyBottomSheet()
        }
    }
}