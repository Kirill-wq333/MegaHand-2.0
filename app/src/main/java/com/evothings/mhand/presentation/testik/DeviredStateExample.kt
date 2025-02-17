package com.evothings.mhand.presentation.testik

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnrememberedMutableState")
@Composable
fun DerivedStateExample() {
    val count = remember { mutableStateOf(0) }
    val isEven = derivedStateOf { count.value % 2 == 0 }

    Column {
        Text(text = "Count: ${count.value}")
        Text(text = "Is even: ${isEven.value}")
        Button(onClick = { count.value++ }) {
            Text("Increment")
        }
    }
}

@Preview
@Composable
private fun DerivedState() {
    DerivedStateExample()
}