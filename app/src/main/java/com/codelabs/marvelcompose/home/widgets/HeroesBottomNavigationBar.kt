package com.codelabs.marvelcompose.home.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.home.viewmodel.SectionViewModel
import com.codelabs.marvelcompose.ui.theme.LightBlue
import com.codelabs.marvelcompose.ui.theme.LightGreen
import com.codelabs.marvelcompose.ui.theme.LightGrey
import com.codelabs.marvelcompose.ui.theme.LightRed
import com.codelabs.marvelcompose.ui.theme.LightYellow
import com.codelabs.marvelcompose.ui.theme.MainTheme
import com.codelabs.marvelcompose.ui.theme.Red
import com.codelabs.marvelcompose.ui.theme.Typography

enum class Section(val color: Color) {
    characters(LightBlue), comics(LightGreen), series(LightYellow), stories(
        LightRed
    )
}

sealed class HeroeBottomNavigationItem(
    @StringRes val label: Int,
    @DrawableRes val image: Int,
    val color: Color,
) {
    data object Characters : HeroeBottomNavigationItem(
        label = R.string.menu_characters,
        image = R.drawable.captain_america,
        color = Section.characters.color
    )

    data object Comics : HeroeBottomNavigationItem(
        label = R.string.menu_comics,
        image = R.drawable.hulk,
        color = Section.comics.color
    )

    data object Series : HeroeBottomNavigationItem(
        label = R.string.menu_series,
        image = R.drawable.thor,
        color = Section.series.color
    )

    data object Stories : HeroeBottomNavigationItem(
        label = R.string.menu_stories,
        image = R.drawable.iron_man,
        color = Section.stories.color
    )
}

@Composable
fun HeroesBottomNavigationBar(
    sectionViewModel: SectionViewModel,
    items: List<HeroeBottomNavigationItem>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .background(Red)
                .fillMaxWidth()
                .height(2.dp),
        )
        BottomAppBar(
            modifier = Modifier.background(MaterialTheme.colorScheme.primary),
            contentPadding = PaddingValues(top = 10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                items.forEach {
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.primary,
                            selectedIconColor = it.color,
                            selectedTextColor = it.color,
                            unselectedIconColor = LightGrey,
                            unselectedTextColor = LightGrey
                        ),
                        label = {
                            Text(
                                text = stringResource(it.label),
                                style = Typography.titleLarge
                            )
                        },
                        icon = {
                            Icon(
                                painterResource(id = it.image),
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.CenterVertically),
                                contentDescription = stringResource(it.label)
                            )
                        },
                        selected = sectionViewModel.selectedItem == it,
                        onClick = {
                            sectionViewModel.selectedItem = it
                        },
                    )
                }
            }

        }
    }
}

@DarkLightPreviews
@Composable
fun CharactersHeroesBottomNavigationBarPreview() {
    val sectionViewModel = SectionViewModel().apply {
        selectedItem = HeroeBottomNavigationItem.Characters
    }

    MainTheme {
        HeroesBottomNavigationBar(
            sectionViewModel = sectionViewModel,
            items = listOf(
                HeroeBottomNavigationItem.Characters,
                HeroeBottomNavigationItem.Comics,
                HeroeBottomNavigationItem.Series,
                HeroeBottomNavigationItem.Stories,
            )
        )
    }
}

@DarkLightPreviews
@Composable
fun ComicsHeroesBottomNavigationBarPreview() {
    val sectionViewModel = SectionViewModel().apply {
        selectedItem = HeroeBottomNavigationItem.Comics
    }

    MainTheme {
        HeroesBottomNavigationBar(
            sectionViewModel = sectionViewModel,
            items = listOf(
                HeroeBottomNavigationItem.Characters,
                HeroeBottomNavigationItem.Comics,
                HeroeBottomNavigationItem.Series,
                HeroeBottomNavigationItem.Stories,
            )
        )
    }
}

@DarkLightPreviews
@Composable
fun SeriesHeroesBottomNavigationBarPreview() {
    val sectionViewModel = SectionViewModel().apply {
        selectedItem = HeroeBottomNavigationItem.Series
    }

    MainTheme {
        HeroesBottomNavigationBar(
            sectionViewModel = sectionViewModel,
            items = listOf(
                HeroeBottomNavigationItem.Characters,
                HeroeBottomNavigationItem.Comics,
                HeroeBottomNavigationItem.Series,
                HeroeBottomNavigationItem.Stories,
            )
        )
    }
}

@DarkLightPreviews
@Composable
fun StoriesHeroesBottomNavigationBarPreview() {
    val sectionViewModel = SectionViewModel().apply {
        selectedItem = HeroeBottomNavigationItem.Stories
    }

    MainTheme {
        HeroesBottomNavigationBar(
            sectionViewModel = sectionViewModel,
            items = listOf(
                HeroeBottomNavigationItem.Characters,
                HeroeBottomNavigationItem.Comics,
                HeroeBottomNavigationItem.Series,
                HeroeBottomNavigationItem.Stories,
            )
        )
    }
}