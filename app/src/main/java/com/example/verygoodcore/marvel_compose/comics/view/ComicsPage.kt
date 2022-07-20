package com.example.verygoodcore.marvel_compose.comics.view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.verygoodcore.marvel_compose.R
import com.example.verygoodcore.marvel_compose.common.widget.UnderConstructionView
import com.example.verygoodcore.marvel_compose.ui.theme.Typography

@Composable
fun ComicsPage() {
    Row(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.Center) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(R.string.menu_comics), style = Typography.body1)
            UnderConstructionView()
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun ComicsPageDarkPreview() {
    MaterialTheme() {
        ComicsPage()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun ComicsPageLightPreview() {
    MaterialTheme() {
        ComicsPage()
    }
}