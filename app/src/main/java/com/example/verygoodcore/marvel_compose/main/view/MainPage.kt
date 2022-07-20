package com.example.verygoodcore.marvel_compose.main.view

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.verygoodcore.marvel_compose.home.view.HomePage
import com.example.verygoodcore.marvel_compose.login.view.LoginPage
import com.example.verygoodcore.marvel_compose.splash.view.SplashPage
import com.example.verygoodcore.marvel_compose.ui.theme.MainTheme

@Composable
fun MainPage() {
    val navController = rememberNavController()

    MainTheme() {
        NavHost(navController = navController, startDestination = "splash") {
            composable("splash") { SplashPage(navController) }
            composable("home") { HomePage(navController) }
            composable("login") { LoginPage(navController) }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun MainPageDarkPreview() {
    MainTheme() {
        SplashPage()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun MainPageLightPreview() {
    MainTheme() {
        SplashPage()
    }
}