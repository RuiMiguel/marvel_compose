package com.codelabs.marvelcompose.characters.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codelabs.marvelcompose.DarkLightPreviews
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.common.widget.UnderConstructionView
import com.codelabs.marvelcompose.ui.theme.Typography

@Composable
fun CharactersPage() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.menu_characters), style = Typography.bodyLarge)
            UnderConstructionView()
        }
    }
}


@DarkLightPreviews
@Composable
fun CharactersPageDarkPreview() {
    MaterialTheme() {
        CharactersPage()
    }
}