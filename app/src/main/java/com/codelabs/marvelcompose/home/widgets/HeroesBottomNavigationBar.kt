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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.ui.theme.LightBlue
import com.codelabs.marvelcompose.ui.theme.LightGreen
import com.codelabs.marvelcompose.ui.theme.LightGrey
import com.codelabs.marvelcompose.ui.theme.LightRed
import com.codelabs.marvelcompose.ui.theme.LightYellow
import com.codelabs.marvelcompose.ui.theme.MainTheme
import com.codelabs.marvelcompose.ui.theme.Red
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

enum class Section(val color: Color) {
    Characters(LightBlue),
    Comics(LightGreen),
    Series(LightYellow),
    Stories(LightRed)
}

sealed class HeroesBottomNavigationItem(
    @StringRes val label: Int,
    @DrawableRes val image: Int,
    val color: Color
) {
    data object Characters : HeroesBottomNavigationItem(
        label = R.string.menu_characters,
        image = R.drawable.captain_america,
        color = Section.Characters.color
    )

    data object Comics : HeroesBottomNavigationItem(
        label = R.string.menu_comics,
        image = R.drawable.hulk,
        color = Section.Comics.color
    )

    data object Series : HeroesBottomNavigationItem(
        label = R.string.menu_series,
        image = R.drawable.thor,
        color = Section.Series.color
    )

    data object Stories : HeroesBottomNavigationItem(
        label = R.string.menu_stories,
        image = R.drawable.iron_man,
        color = Section.Stories.color
    )
}

@Composable
fun HeroesBottomNavigationBar(
    items: PersistentList<HeroesBottomNavigationItem>,
    modifier: Modifier = Modifier,
    isItemSelected: (HeroesBottomNavigationItem) -> Boolean = { false },
    onItemClick: (HeroesBottomNavigationItem) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
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
                                style = MaterialTheme.typography.titleLarge
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
                        selected = isItemSelected(it),
                        onClick = { onItemClick(it) },
                    )
                }
            }

        }
    }
}

@DarkLightPreviews
@Composable
fun CharactersHeroesBottomNavigationBarPreview() {
    MainTheme {
        HeroesBottomNavigationBar(
            isItemSelected = { it == HeroesBottomNavigationItem.Characters },
            onItemClick = {},
            items = persistentListOf(
                HeroesBottomNavigationItem.Characters,
                HeroesBottomNavigationItem.Comics,
                HeroesBottomNavigationItem.Series,
                HeroesBottomNavigationItem.Stories,
            )
        )
    }
}

@DarkLightPreviews
@Composable
fun ComicsHeroesBottomNavigationBarPreview() {
    MainTheme {
        HeroesBottomNavigationBar(
            isItemSelected = { it == HeroesBottomNavigationItem.Comics },
            onItemClick = {},
            items = persistentListOf(
                HeroesBottomNavigationItem.Characters,
                HeroesBottomNavigationItem.Comics,
                HeroesBottomNavigationItem.Series,
                HeroesBottomNavigationItem.Stories,
            )
        )
    }
}

@DarkLightPreviews
@Composable
fun SeriesHeroesBottomNavigationBarPreview() {
    MainTheme {
        HeroesBottomNavigationBar(
            isItemSelected = { it == HeroesBottomNavigationItem.Series },
            onItemClick = {},
            items = persistentListOf(
                HeroesBottomNavigationItem.Characters,
                HeroesBottomNavigationItem.Comics,
                HeroesBottomNavigationItem.Series,
                HeroesBottomNavigationItem.Stories,
            )
        )
    }
}

@DarkLightPreviews
@Composable
fun StoriesHeroesBottomNavigationBarPreview() {
    MainTheme {
        HeroesBottomNavigationBar(
            isItemSelected = { it == HeroesBottomNavigationItem.Stories },
            onItemClick = {},
            items = persistentListOf(
                HeroesBottomNavigationItem.Characters,
                HeroesBottomNavigationItem.Comics,
                HeroesBottomNavigationItem.Series,
                HeroesBottomNavigationItem.Stories,
            )
        )
    }
}