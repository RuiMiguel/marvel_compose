package com.codelabs.marvelcompose.common.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.ui.theme.Typography

@Composable
fun UnderConstructionView() {
    val sentences = LocalContext.current.resources.getStringArray(R.array.deadpool_messages)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.under_construction),
            modifier = Modifier.padding(15.dp),
            style = Typography.displayLarge
        )
        Image(
            painterResource(R.drawable.wait),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            contentDescription = stringResource(R.string.under_construction)
        )
        Text(
            text = sentences[(sentences.indices).random()],
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            maxLines = Int.MAX_VALUE,
            style = Typography.bodyLarge
        )
    }
}

@DarkLightPreviews
@Composable
fun UnderConstructionViewPreview() {
    MaterialTheme() {
        UnderConstructionView()
    }
}