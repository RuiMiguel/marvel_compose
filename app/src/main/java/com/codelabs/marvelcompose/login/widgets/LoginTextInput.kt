package com.codelabs.marvelcompose.login.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginTextInput(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        label = { Text(label) },
        maxLines = 1,
        singleLine = true,
        onValueChange = onValueChange
    )
}