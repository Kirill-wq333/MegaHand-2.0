package com.evothings.mhand.presentation.feature.shared.header.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.notification.model.Notification
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.SmallButton
import com.evothings.mhand.presentation.feature.shared.header.ui.notifications.components.Notification

@Composable
fun NotificationsTray(
    list: List<Notification>,
    onClickUpdate: () -> Unit,
    removeSingle: (Int) -> Unit,
    clearAll: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.onSecondary,
                shape = shapes.large.copy( // Reset top corners
                    topStart = CornerSize(0.dp),
                    topEnd = CornerSize(0.dp)
                )
            )
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (list.isEmpty()) {
            item {
                Text(
                    modifier = Modifier.padding(top = 44.dp, bottom = 110.dp),
                    text = stringResource(R.string.no_notifications_yet),
                    style = typography.headlineMedium,
                    color = colorScheme.secondary.copy(0.4f),
                )
            }
            return@LazyColumn
        }

        items(
            items = list,
            key = { it.id }
        ) { model ->
            Notification(
                model = model,
                onSwipe = removeSingle,
                onClickUpdate = onClickUpdate
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 12.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                SmallButton(
                    text = stringResource(R.string.clear_all),
                    backgroundColor = colorScheme.secondary.copy(0.05f),
                    onClick = clearAll
                )
            }
        }

    }

}