package com.codelabs.marvelcompose.home.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codelabs.marvelcompose.characters.view.CharactersPage
import com.codelabs.marvelcompose.comics.view.ComicsPage
import com.codelabs.marvelcompose.home.viewmodel.SectionViewModel
import com.codelabs.marvelcompose.home.widget.HeroeBottomNavigationItem
import com.codelabs.marvelcompose.home.widget.HeroesAppBar
import com.codelabs.marvelcompose.home.widget.HeroesBottomNavigationBar
import com.codelabs.marvelcompose.navigation.PageRoute
import com.codelabs.marvelcompose.series.view.SeriesPage
import com.codelabs.marvelcompose.stories.view.StoriesPage
import com.codelabs.marvelcompose.ui.theme.MainTheme

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
            Surface(color = MaterialTheme.colorScheme.background) {
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