package com.evothings.mhand.presentation.feature.shared.picker.time

//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyListState
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
//import com.evothings.mhand.presentation.feature.shared.button.Chip
//import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
//import com.evothings.mhand.presentation.feature.shared.text.util.addLeadingZero
//import com.evothings.mhand.presentation.theme.MegahandTheme
//import com.evothings.mhand.presentation.theme.spacers
//import kotlin.random.Random
//
//private const val itemHeight = 44f
//private const val verticalPadding = 12f
//
//@Composable
//fun TimePickerBottomSheet(
//    hourToSet: String,
//    minuteToSet: String,
//    onDismissRequest: () -> Unit,
//    onChangeTime: (String) -> Unit,
//) {
//    MhandModalBottomSheet(
//        onDismissRequest = onDismissRequest
//    ) {
//        TimePickerModal(
//            modifier = Modifier.modalBottomSheetPadding(),
//            hourToSet = hourToSet,
//            minuteToSet = minuteToSet,
//            onChangeTime = onChangeTime
//        )
//    }
//}
//
//@Composable
//private fun TimePickerModal(
//    modifier: Modifier,
//    hourToSet: String,
//    minuteToSet: String,
//    onChangeTime: (String) -> Unit,
//) {
//
//    val hours = remember {
//        (0..23)
//            .map { it.addLeadingZero() }
//            .addShadowItems()
//    }
//
//    val minutes = remember {
//        (0..55 step 5)
//            .map { it.addLeadingZero() }
//            .addShadowItems()
//    }
//
//    val itemsOffset = 2
//
//    var selectedHourIndex by remember {
//        val initial = hours.indexOf(hourToSet).coerceAtLeast(itemsOffset)
//        mutableIntStateOf(initial)
//    }
//    var selectedMinuteIndex by remember {
//        val initial = minutes.indexOf(minuteToSet).coerceAtLeast(itemsOffset)
//        mutableIntStateOf(initial)
//    }
//
//    val hoursListState = rememberLazyListState()
//    val minutesListState = rememberLazyListState()
//
//    LaunchedEffect(selectedHourIndex, selectedMinuteIndex) {
//        val hour = hours[selectedHourIndex]
//        val minute = minutes[selectedMinuteIndex]
//        val time = "$hour:$minute"
//        onChangeTime(time)
//    }
//
//
//    val pickerHeight = remember {
//        (itemHeight + verticalPadding * 2) * 4
//    }
//
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(pickerHeight.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//
//        Spacer(modifier = Modifier)
//
//        TimePickerList(
//            listState = hoursListState,
//            items = hours,
//            selectedItem = hours[selectedHourIndex],
//            onSelect = { selectedHourIndex = it }
//        )
//
//        Spacer(modifier = Modifier)
//
//        Box(
//            modifier = Modifier.fillMaxHeight(),
//            contentAlignment = Alignment.Center,
//        ) {
//            Text(
//                text = ":",
//                style = MaterialTheme.typography.labelLarge,
//                color = MaterialTheme.colorScheme.secondary
//            )
//        }
//
//        Spacer(modifier = Modifier)
//
//        TimePickerList(
//            listState = minutesListState,
//            items = minutes,
//            selectedItem = minutes[selectedMinuteIndex],
//            onSelect = { selectedMinuteIndex = it }
//        )
//
//        Spacer(modifier = Modifier)
//
//    }
//
//}
//
//@Composable
//private fun TimePickerList(
//    listState: LazyListState,
//    items: List<String>,
//    selectedItem: String,
//    onSelect: (Int) -> Unit
//) {
//
//    val itemOffset by remember {
//        derivedStateOf {
//            listState.firstVisibleItemScrollOffset
//        }
//    }
//
//    LaunchedEffect(itemOffset) {
//        val itemsOffset = if (itemOffset > itemHeight) 3 else 2
//        val selectedItemIndex = listState.firstVisibleItemIndex + itemsOffset
//        onSelect(selectedItemIndex)
//    }
//
//    LaunchedEffect(listState.isScrollInProgress) {
//        val scrollOffset = listState.firstVisibleItemScrollOffset
//        if (!listState.isScrollInProgress && scrollOffset > 0) {
//            val scrollTo = when {
//                scrollOffset > itemHeight / 2 -> listState.firstVisibleItemIndex + 1
//                else -> listState.firstVisibleItemIndex
//            }
//            listState.animateScrollToItem(scrollTo)
//        }
//    }
//
//    LazyColumn(
//        modifier = Modifier.fillMaxHeight(),
//        state = listState,
//        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
//    ) {
//        items(
//            items = items,
//            key = { it.ifEmpty { Random.nextInt() } }
//        ) { item ->
//            Box(
//                modifier = Modifier
//                    .alpha(if (item.isEmpty()) 0.0f else 1.0f)
//                    .size(width = 54.dp, height = 44.dp)
//            ) {
//                Chip(
//                    text = item,
//                    enabled = (item == selectedItem),
//                    onClick = {}
//                )
//            }
//        }
//    }
//
//}
//
//private fun List<String>.addShadowItems(): List<String> {
//    val items = this
//    val newList: ArrayList<String> = arrayListOf()
//    val shadowItems = arrayOf("", "")
//    val withShadowItems = newList.apply {
//        addAll(shadowItems)
//        addAll(items)
//        addAll(shadowItems)
//    }
//    return withShadowItems
//}
//
//@Preview
//@Composable
//private fun TimePickerModalPreview() {
//    MegahandTheme(false) {
//        TimePickerBottomSheet(
//            hourToSet = "15",
//            minuteToSet = "00",
//            onDismissRequest = {},
//            onChangeTime = {}
//        )
//    }
//}