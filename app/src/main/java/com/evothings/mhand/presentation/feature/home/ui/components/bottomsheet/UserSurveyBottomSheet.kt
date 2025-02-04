package com.evothings.mhand.presentation.feature.home.ui.components.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.FilledButton
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun UserSurveyBottomSheet(
    onSubmit: (Int) -> Unit,
) {

    val userSurveyEntries = listOf(
        "Промо на лисовках",
        "Наружная реклама(щиты)",
        "Интернет",
        "Радио-реклама",
        "Реклама по телевидению"
    )

    val chosenIndex = remember { mutableIntStateOf(0) }

    Column(
      modifier = Modifier
          .fillMaxWidth()
          .padding(MaterialTheme.paddings.extraGiant),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.where_from_you_know_us),
            color = colorScheme.secondary,
            style = MegahandTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Text(
            text = stringResource(R.string.can_choose_several_answers),
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        repeat(userSurveyEntries.size){
            SurveyItem(
                title = userSurveyEntries[it],
                chosen = it == chosenIndex.intValue,
                onChoose = { chosenIndex.intValue = it }
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        FilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            label = stringResource(R.string.send),
            onClick = { onSubmit(chosenIndex.intValue) }
        )
    }
}

@Composable
private fun SurveyItem(
    title: String,
    chosen: Boolean,
    onChoose: () -> Unit
) {

    val checkboxIconResId = if (chosen)
        R.drawable.ic_radio_button_active
    else
        R.drawable.ic_radio_button_disabled

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onChoose
            )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.giant),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = colorScheme.secondary,
                style = MegahandTypography.bodyLarge
            )
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = checkboxIconResId),
                contentDescription = null
            )
        }
    }

}