package com.example.verygoodcore.marvel_compose.main.view

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.verygoodcore.marvel_compose.navigation.AppNavigation
import com.example.verygoodcore.marvel_compose.navigation.Navigator
import com.example.verygoodcore.marvel_compose.splash.view.SplashPage
import com.example.verygoodcore.marvel_compose.ui.theme.SplashTheme

@Composable
fun MainPage() {
    AppNavigation(navController = rememberNavController(), navigator = Navigator())
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun MainPageDarkPreview() {
    SplashTheme() {
        SplashPage()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun MainPageLightPreview() {
    SplashTheme() {
        SplashPage()
    }
}