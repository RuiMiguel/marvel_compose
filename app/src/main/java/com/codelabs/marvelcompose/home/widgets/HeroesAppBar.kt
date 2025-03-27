package com.codelabs.marvelcompose.home.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.ui.theme.MainTheme
import com.codelabs.marvelcompose.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroesAppBar(
    withBack: Boolean = false,
    withActions: Boolean = false,
    onBack: () -> Unit = {},
    onLoginAction: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .shadow(8.dp),
    ) {
        TopAppBar(
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(id = R.drawable.placeholder),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxHeight(),
                        contentDescription = stringResource(R.string.app_name)
                    )
                }
            },
            navigationIcon = {
                if (withBack) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            },
            actions = {
                if (withActions) {
                    IconButton(onClick = onLoginAction) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = stringResource(R.string.login)
                        )
                    }
                } else {
                    Box(modifier = Modifier.width(48.dp))
                }
            },
        )
        Box(
            modifier = Modifier
                .background(Red)
                .fillMaxWidth()
                .height(2.dp),
        )
    }
}

@DarkLightPreviews
@Composable
fun HeroesAppBarPreview() {
    MainTheme() {
        HeroesAppBar()
    }
}

@DarkLightPreviews
@Composable
fun HeroesAppBarWithActionsPreview() {
    MainTheme() {
        HeroesAppBar(withActions = true)
    }
}

@DarkLightPreviews
@Composable
fun HeroesAppBarWithBackPreview() {
    MainTheme() {
        HeroesAppBar(withBack = true)
    }
}

@DarkLightPreviews
@Composable
fun HeroesAppBarWithBackAndActionsPreview() {
    MainTheme() {
        HeroesAppBar(withBack = true, withActions = true)
    }
}