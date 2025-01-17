package com.evothings.mhand.presentation.feature.home.ui.components.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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

@Composable
fun UserSurveyBottomSheet(
    onSubmit: (Int) -> Unit
) {

    val userSurveyEntries = listOf<String>(
        "Промо",
        "Наружная реклама",
        "Интернет",
        "Радио-реклама",
        "Реклама по телевидению"
    )

    val chosenIndex = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 15.dp,
                bottom = 20.dp
            )
            .offset(x = 0.dp, y = (-10).dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.where_from_you_know_us),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.can_choose_several_answers),
            style = MaterialTheme.typography.labelMedium.copy(MaterialTheme.colorScheme.secondary.copy(0.4f))
        )
        repeat(userSurveyEntries.size) {
            SurveyItem(
                title = userSurveyEntries[it],
                chosen = it == chosenIndex.intValue,
                onChoose = { chosenIndex.intValue = it }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
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
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onChoose
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium
            )
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = checkboxIconResId),
                contentDescription = null
            )
        }
    }

}