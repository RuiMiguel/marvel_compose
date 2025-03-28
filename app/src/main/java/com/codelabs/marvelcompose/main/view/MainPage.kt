package com.codelabs.marvelcompose.main.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.navigation.AppNavigation
import com.codelabs.marvelcompose.navigation.Navigator
import com.codelabs.marvelcompose.splash.view.SplashPage
import com.codelabs.marvelcompose.ui.theme.SplashTheme

@Composable
fun MainPage() {
    val navController: NavHostController = rememberNavController()
    AppNavigation(navController = navController, navigator = Navigator())
}

@DarkLightPreviews
@Composable
fun MainPagePreview() {
    SplashTheme() {
        SplashPage()
    }
}