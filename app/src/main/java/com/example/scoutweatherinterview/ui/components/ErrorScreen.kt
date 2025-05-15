package com.example.scoutweatherinterview.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, errorText: String, content: @Composable (() -> Unit)? = null) {
    Box(
        modifier = modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(modifier = modifier.padding(16.dp), text = errorText)
            content?.invoke()
        }
    }
}
