package com.example.verygoodcore.marvel_compose.common.widget

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.verygoodcore.marvel_compose.R
import com.example.verygoodcore.marvel_compose.ui.theme.Typography

@Composable
fun UnderConstructionView() {
    val sentences = LocalContext.current.resources.getStringArray(R.array.deadpool_messages)

    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(R.string.under_construction),
            modifier = Modifier.padding(15.dp),
            style = Typography.h1)
        Image(painterResource(R.drawable.wait),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            contentDescription = stringResource(R.string.under_construction))
        Text(text = sentences[(sentences.indices).random()],
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            maxLines = Int.MAX_VALUE,
            style = Typography.body1)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun UnderConstructionViewDarkPreview() {
    MaterialTheme() {
        UnderConstructionView()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun UnderConstructionViewLightPreview() {
    MaterialTheme() {
        UnderConstructionView()
    }
}