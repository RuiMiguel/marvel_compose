package com.example.verygoodcore.marvel_compose.home.view

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.verygoodcore.marvel_compose.characters.view.CharactersPage
import com.example.verygoodcore.marvel_compose.comics.view.ComicsPage
import com.example.verygoodcore.marvel_compose.home.viewmodel.SectionViewModel
import com.example.verygoodcore.marvel_compose.home.widget.HeroeBottomNavigationItem
import com.example.verygoodcore.marvel_compose.home.widget.HeroesAppBar
import com.example.verygoodcore.marvel_compose.home.widget.HeroesBottomNavigationBar
import com.example.verygoodcore.marvel_compose.navigation.PageRoute
import com.example.verygoodcore.marvel_compose.series.view.SeriesPage
import com.example.verygoodcore.marvel_compose.stories.view.StoriesPage
import com.example.verygoodcore.marvel_compose.ui.theme.MainTheme

@Composable
fun HomePage(navController: NavController? = null) {
    val sectionViewModel = hiltViewModel<SectionViewModel>()

    MainTheme {
        HomeView(navController = navController, sectionViewModel = sectionViewModel)
    }
}

@Composable
fun HomeView(navController: NavController? = null, sectionViewModel: SectionViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HeroesAppBar(
                withActions = true,
                onLoginAction = {
                    navController?.navigate(PageRoute.Login.route) {
                        popUpTo(PageRoute.Splash.route) { inclusive = true }
                    }
                },
            )
        },
        bottomBar = {
            HeroesBottomNavigationBar(
                sectionViewModel = sectionViewModel,
                items = listOf(
                    HeroeBottomNavigationItem.Characters,
                    HeroeBottomNavigationItem.Comics,
                    HeroeBottomNavigationItem.Series,
                    HeroeBottomNavigationItem.Stories,
                ),
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Surface(color = MaterialTheme.colors.background) {
                when (sectionViewModel.selectedItem) {
                    HeroeBottomNavigationItem.Characters -> CharactersPage()
                    HeroeBottomNavigationItem.Comics -> ComicsPage()
                    HeroeBottomNavigationItem.Series -> SeriesPage()
                    HeroeBottomNavigationItem.Stories -> StoriesPage()
                }
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun HomePageDarkPreview() {
    HomePage()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun HomePageLightPreview() {
    HomePage()
}