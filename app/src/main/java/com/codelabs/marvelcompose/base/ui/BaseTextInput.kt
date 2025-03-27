package com.codelabs.marvelcompose.base.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codelabs.marvelcompose.DarkLightPreviews
import com.codelabs.marvelcompose.ui.theme.MainTheme

@Composable
fun BaseTextInput(
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

@DarkLightPreviews
@Composable
private fun FilledBaseTextInputPreview() {
    MainTheme {
        BaseTextInput(
            label = "text input",
            value = "value",
            onValueChange = { },
        )
    }
}

@DarkLightPreviews
@Composable
private fun EmptyBaseTextInputPreview() {
    MainTheme {
        BaseTextInput(
            label = "text input",
            value = "",
            onValueChange = { },
        )
    }
}