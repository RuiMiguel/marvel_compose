package com.codelabs.marvelcompose.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.characters.view.CharactersPage
import com.codelabs.marvelcompose.comics.view.ComicsPage
import com.codelabs.marvelcompose.home.viewmodel.SectionViewModel
import com.codelabs.marvelcompose.home.widgets.HeroesBottomNavigationItem
import com.codelabs.marvelcompose.home.widgets.HeroesAppBar
import com.codelabs.marvelcompose.home.widgets.HeroesBottomNavigationBar
import com.codelabs.marvelcompose.navigation.PageRoute
import com.codelabs.marvelcompose.series.view.SeriesPage
import com.codelabs.marvelcompose.stories.view.StoriesPage
import com.codelabs.marvelcompose.ui.theme.MainTheme

@Composable
fun HomePage(navController: NavController? = null) {
    MainTheme {
        HomeView(navController = navController, sectionViewModel = hiltViewModel())
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
                isItemSelected = { sectionViewModel.selectedNavigationItem == it },
                onItemClick = sectionViewModel::selectNavigationItem,
                items = listOf(
                    HeroesBottomNavigationItem.Characters,
                    HeroesBottomNavigationItem.Comics,
                    HeroesBottomNavigationItem.Series,
                    HeroesBottomNavigationItem.Stories,
                ),
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Surface(color = MaterialTheme.colorScheme.background) {
                when (sectionViewModel.selectedNavigationItem) {
                    HeroesBottomNavigationItem.Characters -> CharactersPage()
                    HeroesBottomNavigationItem.Comics -> ComicsPage()
                    HeroesBottomNavigationItem.Series -> SeriesPage()
                    HeroesBottomNavigationItem.Stories -> StoriesPage()
                }
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun HomePagePreview() {
    HomePage()
}