package com.example.verygoodcore.marvel_compose.login.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.verygoodcore.marvel_compose.characters.view.CharactersPage
import com.example.verygoodcore.marvel_compose.comics.view.ComicsPage
import com.example.verygoodcore.marvel_compose.home.viewmodel.SectionViewModel
import com.example.verygoodcore.marvel_compose.home.widget.HeroeBottomNavigationItem
import com.example.verygoodcore.marvel_compose.home.widget.HeroesAppBar
import com.example.verygoodcore.marvel_compose.home.widget.HeroesBottomNavigationBar
import com.example.verygoodcore.marvel_compose.login.viewmodel.LoginViewModel
import com.example.verygoodcore.marvel_compose.series.view.SeriesPage
import com.example.verygoodcore.marvel_compose.stories.view.StoriesPage
import com.example.verygoodcore.marvel_compose.ui.theme.MainTheme

@Composable
fun LoginPage(navController: NavController? = null) {
    val loginViewModel = LoginViewModel()
    LoginView(loginViewModel)
}

@Composable
fun LoginView(loginViewModel: LoginViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HeroesAppBar(withActions = false)
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Surface(color = MaterialTheme.colors.background) {

            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun LoginPageDarkPreview() {
    MainTheme() {
        LoginPage()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun LoginPageLightPreview() {
    MainTheme() {
        LoginPage()
    }
}