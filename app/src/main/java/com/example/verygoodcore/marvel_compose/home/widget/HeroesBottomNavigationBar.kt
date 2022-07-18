package com.example.verygoodcore.marvel_compose.home.widget

import android.content.res.Configuration
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
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.verygoodcore.marvel_compose.R
import com.example.verygoodcore.marvel_compose.home.viewmodel.SectionViewModel
import com.example.verygoodcore.marvel_compose.ui.theme.MainTheme
import com.example.verygoodcore.marvel_compose.ui.theme.Typography
import com.example.verygoodcore.marvel_compose.ui.theme.lightBlue
import com.example.verygoodcore.marvel_compose.ui.theme.lightGreen
import com.example.verygoodcore.marvel_compose.ui.theme.lightGrey
import com.example.verygoodcore.marvel_compose.ui.theme.lightRed
import com.example.verygoodcore.marvel_compose.ui.theme.lightYellow
import com.example.verygoodcore.marvel_compose.ui.theme.red

enum class Section(val color: Color) { characters(lightBlue), comics(lightGreen), series(lightYellow), stories(lightRed) }

sealed class HeroeBottomNavigationItem(
    @StringRes val label: Int,
    @DrawableRes val image: Int,
    val color: Color,
) {
    object Characters : HeroeBottomNavigationItem(label = R.string.menu_characters, image = R.drawable.captain_america, color = Section.characters.color)
    object Comics : HeroeBottomNavigationItem(label = R.string.menu_comics, image = R.drawable.hulk, color = Section.comics.color)
    object Series : HeroeBottomNavigationItem(label = R.string.menu_series, image = R.drawable.thor, color = Section.series.color)
    object Stories : HeroeBottomNavigationItem(label = R.string.menu_stories, image = R.drawable.iron_man, color = Section.stories.color)
}

@Composable
fun HeroesBottomNavigationBar(sectionViewModel: SectionViewModel, items: List<HeroeBottomNavigationItem>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .background(red)
                .fillMaxWidth()
                .height(2.dp),
        )
        BottomAppBar(
            backgroundColor = MaterialTheme.colors.primary,
            contentPadding = PaddingValues(top = 10.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                items.forEach {
                    BottomNavigationItem(
                        modifier = Modifier,
                        label = { Text(text = stringResource(it.label), style = Typography.subtitle1) },
                        icon = {
                            Icon(painterResource(id = it.image), modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.CenterVertically), contentDescription = stringResource(it.label))
                        },
                        selected = sectionViewModel.selectedItem == it,
                        selectedContentColor = it.color,
                        unselectedContentColor = lightGrey,
                        onClick = {
                            sectionViewModel.selectedItem = it
                        },
                    )
                }
            }

        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun HeroesBottomNavigationBarDarkPreview() {
    MainTheme() {
        HeroesBottomNavigationBar(
            sectionViewModel = SectionViewModel(),
            items = listOf(
                HeroeBottomNavigationItem.Characters,
                HeroeBottomNavigationItem.Comics,
                HeroeBottomNavigationItem.Series,
                HeroeBottomNavigationItem.Stories,
            )
        )
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun HeroesBottomNavigationBarLightPreview() {
    MainTheme() {
        HeroesBottomNavigationBar(
            sectionViewModel = SectionViewModel(),
            items = listOf(
                HeroeBottomNavigationItem.Characters,
                HeroeBottomNavigationItem.Comics,
                HeroeBottomNavigationItem.Series,
                HeroeBottomNavigationItem.Stories,
            )
        )
    }
}