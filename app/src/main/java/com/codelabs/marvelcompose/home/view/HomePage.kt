package com.codelabs.marvelcompose.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.characters.view.CharactersPage
import com.codelabs.marvelcompose.comics.view.ComicsPage
import com.codelabs.marvelcompose.home.viewmodel.SectionViewModel
import com.codelabs.marvelcompose.home.widgets.HeroesAppBar
import com.codelabs.marvelcompose.home.widgets.HeroesBottomNavigationBar
import com.codelabs.marvelcompose.home.widgets.HeroesBottomNavigationItem
import com.codelabs.marvelcompose.navigation.Navigator
import com.codelabs.marvelcompose.series.view.SeriesPage
import com.codelabs.marvelcompose.stories.view.StoriesPage
import com.codelabs.marvelcompose.ui.theme.MainTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomePage(navigator: Navigator? = null) {
    MainTheme {
        HomeView(navigator = navigator, sectionViewModel = hiltViewModel())
    }
}

@Composable
fun HomeView(navigator: Navigator? = null, sectionViewModel: SectionViewModel) {
    val bottomNavItems = remember {
        persistentListOf(
            HeroesBottomNavigationItem.Characters,
            HeroesBottomNavigationItem.Comics,
            HeroesBottomNavigationItem.Series,
            HeroesBottomNavigationItem.Stories,
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HeroesAppBar(
                withActions = true,
                onLoginAction = { navigator?.toLogin() },
            )
        },
        bottomBar = {
            HeroesBottomNavigationBar(
                isItemSelected = { sectionViewModel.selectedNavigationItem == it },
                onItemClick = sectionViewModel::selectNavigationItem,
                items = bottomNavItems,
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Surface(color = MaterialTheme.colorScheme.background) {
                when (sectionViewModel.selectedNavigationItem) {
                    HeroesBottomNavigationItem.Characters -> CharactersPage()
                    HeroesBottomNavigationItem.Comics -> ComicsPage(navigator = navigator!!)
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