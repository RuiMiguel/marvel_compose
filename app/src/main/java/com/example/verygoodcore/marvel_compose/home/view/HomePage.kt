package com.example.verygoodcore.marvel_compose.home.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.verygoodcore.marvel_compose.characters.view.CharactersPage
import com.example.verygoodcore.marvel_compose.comics.view.ComicsPage
import com.example.verygoodcore.marvel_compose.home.viewmodel.SectionViewModel
import com.example.verygoodcore.marvel_compose.home.widget.HeroeBottomNavigationItem
import com.example.verygoodcore.marvel_compose.home.widget.HeroesAppBar
import com.example.verygoodcore.marvel_compose.home.widget.HeroesBottomNavigationBar
import com.example.verygoodcore.marvel_compose.series.view.SeriesPage
import com.example.verygoodcore.marvel_compose.stories.view.StoriesPage
import com.example.verygoodcore.marvel_compose.ui.theme.MainTheme

@Composable
fun HomePage() {
    val sectionViewModel = SectionViewModel()
    HomeView(sectionViewModel)
}

@Composable
fun HomeView(sectionViewModel: SectionViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HeroesAppBar()
        },
        content = {
            when (sectionViewModel.selectedItem) {
                HeroeBottomNavigationItem.Characters -> CharactersPage()
                HeroeBottomNavigationItem.Comics -> ComicsPage()
                HeroeBottomNavigationItem.Series -> SeriesPage()
                HeroeBottomNavigationItem.Stories -> StoriesPage()
            }
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
    )
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun HomePageDarkPreview() {
    MainTheme() {
        HomePage()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun HomePageLightPreview() {
    MainTheme() {
        HomePage()
    }
}