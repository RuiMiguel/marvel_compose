package com.example.verygoodcore.marvel_compose.home.widget

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.verygoodcore.marvel_compose.R
import com.example.verygoodcore.marvel_compose.ui.theme.MainTheme
import com.example.verygoodcore.marvel_compose.ui.theme.red

@Composable
fun HeroesAppBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp),
    ) {
        TopAppBar(backgroundColor = MaterialTheme.colors.primary) {
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentDescription = stringResource(R.string.app_name))
            }
        }
        Box(
            modifier = Modifier
                .background(red)
                .fillMaxWidth()
                .height(2.dp),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun HeroesAppBarDarkPreview() {
    MainTheme() {
        HeroesAppBar()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun HeroesAppBarLightPreview() {
    MainTheme() {
        HeroesAppBar()
    }
}