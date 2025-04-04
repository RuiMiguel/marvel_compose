package com.codelabs.marvelcompose.common.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.ui.theme.Red
import com.codelabs.marvelcompose.ui.theme.White

@Composable
fun InfoView(
    legal: String,
    counter: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .background(MaterialTheme.colorScheme.outline)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Red)
                .align(Alignment.TopStart)
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp),
            text = legal,
            color = White,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp),
            text = counter.toString(),
            color = White,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@DarkLightPreviews
@Composable
fun InfoViewPreview() {
    MaterialTheme {
        InfoView(
            legal = "Data provided by Marvel. © 2025 Marvel",
            counter = "100 of 1000"
        )
    }
}